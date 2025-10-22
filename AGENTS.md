🎯 Contexto del proyecto

Nombre: CandidatoInfo – Transparencia Electoral Ciudadana (Android, Kotlin + Jetpack Compose)
Objetivo: permitir a la ciudadanía consultar info pública de candidatos (denuncias, proyectos, historial político, datos personales), con búsqueda y comparación. Las vistas mínimas: Inicio, Búsqueda, Detalle y Comparación.

Requerimientos_Candidatos (1)

Hitos (resumen)

D1: prototipo Figma, fuentes oficiales, repo inicial.

D2: estructura base + navegación.

D3: UI de pantallas.

D4: integración de datos (simulados/JSON).

D5: búsqueda avanzada + comparación.

D6: documentación y release v1.0.

Requerimientos_Candidatos (1)

🧱 Arquitectura & convenciones

Capa UI (Compose): ui/screens y ui/components/* (subcarpetas: search/, compare/, profile/, etc.).

Navegación: navigation (NavHost, rutas tipo "inicio", "search", "detalle/{id}", "comparar").

Datos (Room + Repository): data/db, data/dao, data/repository.

Modelos: data/model (Candidato, Propuesta, Denuncia, HistorialCargo, DatosElectorales).

Fuentes locales: app/src/main/assets/app-electoral-datos.json (semilla inicial).

Estilo: Kotlin idiomático, Compose Material 3, colores del proyecto (Gunmetal, Robin Egg Blue, Mint Cream, Naples Yellow, Light Red).
Commits: convenciones tipo Conventional Commits (feat:, fix:, refactor:, docs:…).
Ramas: main protegida; trabajo en feature/*, PR obligatorio con checklist.

📦 Dependencias clave

Room: persistencia y reactividad con Flow.

Gson: parseo de JSON de assets.

Coil: carga de imágenes (AsyncImage) desde foto_url.

Compose Material 3: UI moderna.

Gradle (módulo :app):

implementation("androidx.room:room-runtime:2.6.1")
kapt("androidx.room:room-compiler:2.6.1")
implementation("androidx.room:room-ktx:2.6.1")
implementation("com.google.code.gson:gson:2.10.1")
implementation("io.coil-kt:coil-compose:2.5.0")

🗂️ Modelo de datos (Room)
Entities
// data/model/CandidatoEntity.kt
@Entity(tableName = "candidatos")
data class CandidatoEntity(
@PrimaryKey val id: Int,
val nombre: String,
val partido: String,
val cargo: String,
val region: String,
val foto_url: String,
val dni: Long,
val nacimiento: String,
val biografia: String,
val experiencia: Int,
val estado: String
)

// data/model/PropuestaEntity.kt
@Entity(
tableName = "propuestas",
foreignKeys = [ForeignKey(
entity = CandidatoEntity::class,
parentColumns = ["id"],
childColumns = ["candidatoId"],
onDelete = ForeignKey.CASCADE
)], indices = [Index("candidatoId")]
)
data class PropuestaEntity(
@PrimaryKey(autoGenerate = true) val id: Int = 0,
val candidatoId: Int,
val titulo: String,
val descripcion: String,
val categoria: String,
val prioridad: String
)

// data/model/DenunciaEntity.kt
@Entity(
tableName = "denuncias",
foreignKeys = [ForeignKey(
entity = CandidatoEntity::class,
parentColumns = ["id"],
childColumns = ["candidatoId"],
onDelete = ForeignKey.CASCADE
)], indices = [Index("candidatoId")]
)
data class DenunciaEntity(
@PrimaryKey(autoGenerate = true) val id: Int = 0,
val candidatoId: Int,
val titulo: String,
val descripcion: String,
val expediente: String,
val delito: String,
val fecha_denuncia: String,
val estado: String,
val fuente_url: String
)

// data/model/HistorialCargoEntity.kt
@Entity(
tableName = "historial_cargos",
foreignKeys = [ForeignKey(
entity = CandidatoEntity::class,
parentColumns = ["id"],
childColumns = ["candidatoId"],
onDelete = ForeignKey.CASCADE
)], indices = [Index("candidatoId")]
)
data class HistorialCargoEntity(
@PrimaryKey(autoGenerate = true) val id: Int = 0,
val candidatoId: Int,
val cargo: String,
val institucion: String,
val fecha_inicio: String,
val fecha_fin: String,
val descripcion: String
)

Relaciones para UI (POJOs)
data class CandidatoConTodo(
@Embedded val candidato: CandidatoEntity,
@Relation(parentColumn = "id", entityColumn = "candidatoId")
val propuestas: List<PropuestaEntity>,
@Relation(parentColumn = "id", entityColumn = "candidatoId")
val denuncias: List<DenunciaEntity>,
@Relation(parentColumn = "id", entityColumn = "candidatoId")
val historial: List<HistorialCargoEntity>
)

DAO
@Dao
interface CandidatoDao {
@Transaction
@Query("SELECT * FROM candidatos ORDER BY nombre")
fun observarCandidatos(): Flow<List<CandidatoConTodo>>

@Transaction
@Query("SELECT * FROM candidatos WHERE id = :id LIMIT 1")
fun observarCandidato(id: Int): Flow<CandidatoConTodo?>

@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun upsertCandidatos(list: List<CandidatoEntity>)
}

@Dao
interface PropuestaDao {
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun upsert(list: List<PropuestaEntity>)
}

@Dao
interface DenunciaDao {
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun upsert(list: List<DenunciaEntity>)
}

@Dao
interface HistorialCargoDao {
@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun upsert(list: List<HistorialCargoEntity>)
}

Database & seeding desde JSON (assets)
@Database(
entities = [
CandidatoEntity::class, PropuestaEntity::class,
DenunciaEntity::class, HistorialCargoEntity::class
],
version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
abstract fun candidatoDao(): CandidatoDao
abstract fun propuestaDao(): PropuestaDao
abstract fun denunciaDao(): DenunciaDao
abstract fun historialCargoDao(): HistorialCargoDao

companion object {
fun build(context: Context): AppDatabase =
Room.databaseBuilder(context, AppDatabase::class.java, "app_electoral.db")
.addCallback(object : RoomDatabase.Callback() {
override fun onCreate(db: SupportSQLiteDatabase) {
super.onCreate(db)
// Semilla en background
CoroutineScope(Dispatchers.IO).launch {
val database = build(context) // o instancia singleton
}
}
}).build()
}
}


Seeder (repositorio):

class SeedRepository(
private val context: Context,
private val db: AppDatabase
) {
suspend fun seedIfEmpty() {
if (db.candidatoDao().observarCandidatos().firstOrNull().isNullOrEmpty()) {
val raw = context.assets.open("app-electoral-datos.json").bufferedReader().readText()
val dto = Gson().fromJson(raw, DatosElectorales::class.java)

      // Mapear DTO → Entities
      val candidatos = dto.candidatos.map {
        CandidatoEntity(
          id = it.id, nombre = it.nombre, partido = it.partido, cargo = it.cargo,
          region = it.region, foto_url = it.foto_url, dni = it.dni,
          nacimiento = it.nacimiento, biografia = it.biografia,
          experiencia = it.experiencia, estado = it.estado
        )
      }
      db.candidatoDao().upsertCandidatos(candidatos)

      fun cid(n: String) = dto.candidatos.first { it.nombre == n }.id
      db.propuestaDao().upsert(dto.propuestas.map {
        PropuestaEntity(
          candidatoId = cid(it.candidatoNombre),
          titulo = it.titulo, descripcion = it.descripcion,
          categoria = it.categoria, prioridad = it.prioridad
        )
      })
      db.denunciaDao().upsert(dto.denuncias.map {
        DenunciaEntity(
          candidatoId = cid(it.candidatoNombre),
          titulo = it.titulo, descripcion = it.descripcion, expediente = it.expediente,
          delito = it.delito, fecha_denuncia = it.fecha_denuncia,
          estado = it.estado, fuente_url = it.fuente_url
        )
      })
      db.historialCargoDao().upsert(dto.historialCargos.map {
        HistorialCargoEntity(
          candidatoId = cid(it.candidatoNombre),
          cargo = it.cargo, institucion = it.institucion,
          fecha_inicio = it.fecha_inicio, fecha_fin = it.fecha_fin,
          descripcion = it.descripcion
        )
      })
    }
}
}


Nota: añade en tus DTOs (DatosElectorales) el campo de unión candidatoNombre (o candidatoId) para poder mapear a tablas hijas.

🔁 Repository + Flow
class CandidatoRepository(
private val db: AppDatabase,
private val seed: SeedRepository
) {
fun candidatos(): Flow<List<CandidatoConTodo>> =
db.candidatoDao().observarCandidatos()

fun candidato(id: Int): Flow<CandidatoConTodo?> =
db.candidatoDao().observarCandidato(id)

suspend fun ensureSeeded() = seed.seedIfEmpty()
}

🧠 ViewModel (patrón Flow, similar a tu ejemplo)
class CandidatosViewModel(
private val repo: CandidatoRepository
) : ViewModel() {

private val _candidatos = MutableStateFlow<List<CandidatoConTodo>>(emptyList())
val candidatos = _candidatos.asStateFlow()

init {
viewModelScope.launch {
repo.ensureSeeded()
repo.candidatos().collect { _candidatos.value = it }
}
}
}

🗺️ Rutas de navegación (sugeridas)

"inicio" → panel + stats.

"search" → barra + lista filtrable.

"detalle/{id}" → DetailScreen, carga repo.candidato(id).

"comparar" → selección múltiple y comparación lado a lado.

✅ Definition of Done (DoD)

Compila y corre en emulador (API 28+).

Cumple RF del hito actual (ver lista D1–D6).

Requerimientos_Candidatos (1)

UI acorde a Material 3 y paleta del proyecto.

Datos provistos por Room con Flow (no lectura directa del JSON en UI).

Sin crashes, manejo de vacíos/errores.

PR con descripción, capturas y pruebas manuales.

🧪 QA rápido

Buscar por nombre/partido/región.

Abrir detalle desde resultados (imagen con Coil y placeholder).

Ver historial/propuestas/denuncias del candidato.

Comparar 2 candidatos.

Modo offline: DB poblada desde JSON en el primer arranque.

🛠️ Tareas que un agente puede ejecutar

Datos & persistencia

Generar DAOs faltantes y consultas con Flow.

Implementar SeedRepository y ensureSeeded() en el arranque.

Añadir migraciones Room (v2+) si cambia el esquema.

Normalizar DTOs → Entities (añadir candidatoId en JSON si hace falta).

UI

Conectar SearchScreen a repo.candidatos() + filtros.

DetailScreen consume repo.candidato(id) (Flow + collectAsState()).

CompareScreen: selección de 2 IDs y render lado a lado.