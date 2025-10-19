# 📱 AppElectoral - Transparencia Electoral Ciudadana

## 📖 Descripción del Proyecto

Aplicación móvil que permite a los ciudadanos peruanos consultar información pública sobre los candidatos al Congreso y la Presidencia del Perú, incluyendo denuncias, proyectos presentados, historial político y enlaces a fuentes oficiales.

---

## 👥 Equipo de Desarrollo

| Rol | Nombre | Responsabilidades |
|-----|--------|-------------------|
| **Líder Técnico** | Sergio Serva | Arquitectura del proyecto, base de datos (Room), gestión de GitHub, coordinación del equipo |
| **Diseñadora UI/UX** | Yamile Ochoa | Prototipo en Figma, diseño de interfaz, implementación de pantallas, sistema de navegación |
| **Documentador/Tester** | Josep Rivera | Investigación de fuentes, documentación del proyecto, pruebas de funcionalidad |

---

## 🔍 Fuentes de Información Pública Investigadas

### 1. JNE - Jurado Nacional de Elecciones
- **URL:** https://www.jne.gob.pe
- **Plataformas:**
  - Plataforma Electoral: https://plataformaelectoral.jne.gob.pe
  - Voto Informado: https://votoinformado.jne.gob.pe
- **Información disponible:**
  - Hojas de vida de candidatos
  - Planes de gobierno
  - Sentencias por delitos electorales
  - Resoluciones y proclamaciones

### 2. Infogob - Observatorio para la Gobernabilidad
- **URL:** https://infogob.jne.gob.pe
- **Información disponible:**
  - Historial político completo desde 1931
  - Historial partidario de candidatos
  - Cargos públicos anteriores
  - Resultados electorales históricos
  - Afiliación a organizaciones políticas

### 3. ONPE - Oficina Nacional de Procesos Electorales
- **URL:** https://www.onpe.gob.pe
- **Plataformas:**
  - Claridad: https://claridad.onpe.gob.pe
  - Datos Abiertos: https://www.onpe.gob.pe/elecciones/
- **Información disponible:**
  - Resultados electorales
  - Financiamiento de campañas políticas
  - Aportes, ingresos y gastos electorales
  - Datos descargables en CSV/XLSX

### 4. Poder Judicial del Perú
- **URL:** https://www.pj.gob.pe
- **Plataformas:**
  - CEJ - Consulta de Expedientes: https://cej.pj.gob.pe/cej/forms/busquedaform.html
  - REDAM: https://casillas.pj.gob.pe/redam/
- **Información disponible:**
  - Expedientes judiciales
  - Sentencias y resoluciones
  - Registro de Deudores Alimentarios Morosos

### 5. Contraloría General de la República
- **URL:** https://www.contraloria.gob.pe
- **Plataformas:**
  - Sistema de DDJJ: https://apps1.contraloria.gob.pe/ddjj/
  - Portal Ciudadano: https://serviciosenlinea.contraloria.gob.pe/
- **Información disponible:**
  - Declaración Jurada de Ingresos, Bienes y Rentas
  - Declaración Jurada de Intereses
  - Información patrimonial de candidatos y autoridades

### 6. Congreso de la República
- **URL:** https://www.congreso.gob.pe
- **Plataformas:**
  - Proyectos de Ley: https://www.congreso.gob.pe/proyectosdeley
  - Asistencias y Votaciones: https://www.congreso.gob.pe/AsistVotPlenoPermanente/
- **Información disponible:**
  - Proyectos de ley presentados
  - Votaciones nominales de congresistas
  - Asistencia a sesiones plenarias
  - Informes de gestión

---

## 🎨 Prototipo de Diseño

**Enlace a Figma:** https://www.figma.com/design/vVHL6YPTLcVYzOJ4ChxqX3/AppElectoral?node-id=0-1&t=14cjhsqe2e7whMER-1

### Pantallas Principales

El prototipo incluye las siguientes pantallas:

1. **HomeScreen** - Pantalla principal con buscador y listado de candidatos
2. **DetailScreen** - Información completa del candidato seleccionado
3. **SearchScreen** - Búsqueda avanzada con filtros
4. **CompareScreen** - Comparativa entre candidatos

---

## 🏗️ Arquitectura del Proyecto

### Estructura de Paquetes Implementada

```
app/src/main/java/com/proyecto/app_electoral/
├── data/                          # Capa de datos
│   ├── dao/                       # Data Access Objects (Room)
│   │   └── CandidatoDao.kt       # Interface DAO para operaciones CRUD
│   ├── db/                        # Base de datos
│   │   └── AppDataBase.kt        # Configuración de Room Database
│   ├── model/                     # Modelos de datos (Entities)
│   │   ├── Candidato.kt          # Modelo de candidato
│   │   ├── CargoHistorico.kt     # Modelo de historial de cargos
│   │   ├── Denuncia.kt           # Modelo de denuncias
│   │   ├── Propuesta.kt          # Modelo de propuestas
│   │   └── Proyecto.kt           # Modelo de proyectos de ley
│   └── repository/                # Repositorios
│       └── CandidatoRepository.kt # Repositorio para lógica de datos
├── navigation/                    # Sistema de navegación
│   └── AppNavigation.kt          # Configuración del NavHost
├── ui/                            # Capa de presentación
│   ├── screens/                   # Pantallas de la aplicación
│   │   ├── CompareScreen.kt      # Pantalla de comparación
│   │   ├── DetailScreen.kt       # Pantalla de detalle del candidato
│   │   ├── HomeScreen.kt         # Pantalla principal
│   │   └── SearchScreen.kt       # Pantalla de búsqueda avanzada
│   └── theme/                     # Tema y estilos Material 3
└── MainActivity.kt                # Actividad principal
```

---

## 🗺️ Flujo de Navegación

### Estructura de Navegación Implementada

```
┌──────────────────────────────────────────┐
│         HomeScreen (Inicio)              │
│  ┌────────────────────────────────────┐  │
│  │   Barra de búsqueda rápida         │  │
│  └────────────────────────────────────┘  │
│  ┌────────────────────────────────────┐  │
│  │ Lista de candidatos (LazyColumn)   │  │
│  │ • Nombre                           │  │
│  │ • Partido político                 │  │
│  │ • Cargo al que postula             │  │
│  │ • Foto                             │  │
│  └────────────────────────────────────┘  │
└──────────┬───────────────────┬───────────┘
           │                   │
           │ onClick           │ Búsqueda avanzada
           │                   │
           ▼                   ▼
┌──────────────────────┐  ┌──────────────────────┐
│   DetailScreen       │  │   SearchScreen       │
│  ┌────────────────┐  │  │  ┌────────────────┐  │
│  │ Datos          │  │  │  │ Filtros:       │  │
│  │ personales     │  │  │  │ • Por nombre   │  │
│  │ y foto         │  │  │  │ • Por partido  │  │
│  └────────────────┘  │  │  │ • Por región   │  │
│  ┌────────────────┐  │  │  │ • Por cargo    │  │
│  │ Historial de   │  │  │  └────────────────┘  │
│  │ cargos         │  │  └──────────────────────┘
│  └────────────────┘  │
│  ┌────────────────┐  │
│  │ Denuncias y    │  │
│  │ sentencias     │──┼──► onClick(denuncia)
│  └────────────────┘  │
│  ┌────────────────┐  │
│  │ Propuestas y   │  │
│  │ proyectos      │  │
│  └────────────────┘  │
│  ┌────────────────┐  │
│  │ Enlaces a      │  │
│  │ fuentes        │  │
│  │ oficiales      │  │
│  └────────────────┘  │
└──────────────────────┘
           │
           │ Comparar
           ▼
┌──────────────────────┐
│   CompareScreen      │
│  ┌────────────────┐  │
│  │ Seleccionar 2+ │  │
│  │ candidatos     │  │
│  └────────────────┘  │
│  ┌────────────────┐  │
│  │ Vista          │  │
│  │ comparativa    │  │
│  │ lado a lado    │  │
│  └────────────────┘  │
└──────────────────────┘
```

### Rutas de Navegación (Navigation Compose)
- `/home` → Pantalla principal con lista de candidatos
- `/candidato/{id}` → Detalle completo del candidato
- `/buscar` → Búsqueda avanzada con filtros
- `/comparar` → Comparación de múltiples candidatos

---

## 📝 Registro de Implementaciones por Día

### ✅ Día 1 - Completado (Planificación y Diseño)
- [x] **RF01:** Investigación de fuentes oficiales (JNE, ONPE, Congreso, Poder Judicial, Contraloría)
- [x] **RF02:** Prototipo con 4 pantallas principales: HomeScreen, DetailScreen, SearchScreen, CompareScreen
- [x] **RF03:** Prototipo diseñado en Figma con flujo de navegación definido
- [x] **RF04:** Repositorio GitHub creado con README inicial y enlace a Figma
- [x] **RF05:** Roles asignados: Sergio (Líder Técnico), Yamile (Diseñadora UI/UX), Josep (Documentador)

**Entregables:**
- ✔️ Prototipo Figma: https://www.figma.com/design/vVHL6YPTLcVYzOJ4ChxqX3/AppElectoral
- ✔️ Repositorio GitHub inicializado

---

### 🔄 Día 2 - En Progreso (Estructura Base del Proyecto)

#### ✅ Configuración del Proyecto
- [x] **RF06:** Proyecto creado en Android Studio con Kotlin + Jetpack Compose
- [x] **RF07:** Estructura de paquetes implementada:
  - ✔️ `data/` - Capa de datos (dao, db, model, repository)
  - ✔️ `navigation/` - Sistema de navegación
  - ✔️ `ui/` - Capa de presentación (screens, theme)
- [x] **RF08:** Sistema de navegación implementado con Navigation Compose
  - ✔️ `AppNavigation.kt` - NavHost configurado
  - ✔️ Rutas definidas para todas las pantallas
- [x] **RF09:** Sistema de ramas Git configurado para trabajo colaborativo
- [x] **RF10:** Documentación de commits en progreso

#### ✅ Pantallas Creadas (Base)
1. **HomeScreen.kt** - Pantalla principal con lista de candidatos
2. **DetailScreen.kt** - Perfil completo del candidato
3. **SearchScreen.kt** - Búsqueda avanzada con filtros
4. **CompareScreen.kt** - Comparación de candidatos

#### ✅ Modelos de Datos Implementados
- **Candidato.kt** - Entidad principal con datos del candidato
- **CargoHistorico.kt** - Historial de cargos públicos
- **Denuncia.kt** - Denuncias y procesos judiciales
- **Propuesta.kt** - Propuestas de campaña
- **Proyecto.kt** - Proyectos de ley (congresistas)

#### ✅ Base de Datos (Room)
- **AppDataBase.kt** - Configuración de Room Database
- **CandidatoDao.kt** - Interface con operaciones CRUD:
  - Insert, Update, Delete
  - Consultas (getAll, getById, searchByName)
  - Queries con filtros

#### ✅ Repositorio
- **CandidatoRepository.kt** - Patrón Repository implementado
  - Abstracción de la fuente de datos

#### 📦 Dependencias Configuradas

**Rama de Yamile Ochoa (UI/Navigation):**
```gradle
// Navigation Compose
implementation("androidx.navigation:navigation-compose:2.7.7")
```

**Rama de Sergio Serva (Data/Architecture):**
```gradle
// Room Database
val roomVersion = "2.6.1"
implementation("androidx.room:room-runtime:$roomVersion")
kapt("androidx.room:room-compiler:$roomVersion")
implementation("androidx.room:room-ktx:$roomVersion")

// ViewModel + LiveData / StateFlow
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.6")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")
```

**Entregables:**
- ✔️ Proyecto base funcional con navegación entre pantallas
- ✔️ Estructura de paquetes completa y organizada
- ✔️ Base de datos Room configurada
- ✔️ Modelos de datos definidos
- ✔️ Repositorio GitHub actualizado con commits documentados

---

### 📋 Día 3 - Pendiente (Interfaz de Usuario)
- [ ] **RF11:** Implementar pantalla Inicio con barra de búsqueda funcional
- [ ] **RF12:** Mostrar lista de candidatos con cards (nombre, partido, foto, cargo)
- [ ] **RF13:** Crear pantalla Detalle con todos los datos y antecedentes
- [ ] **RF14:** Aplicar diseño Material 3 con paleta de colores institucional
- [ ] **RF15:** Conectar navegación lista → detalle con datos reales

---
