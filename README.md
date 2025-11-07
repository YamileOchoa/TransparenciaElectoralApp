# 📱 Transparencia Electoral App

Aplicación móvil y backend de apoyo que permiten a la ciudadanía peruana consultar información pública y comparativa sobre candidatos políticos.  
El sistema busca promover la **transparencia electoral** mediante el acceso organizado, confiable y visual a información pública de fuentes oficiales del Perú.

---

## 🧱 Estructura del Repositorio

```
TransparenciaElectoralApp/
├── Backend/                      # Proyecto Django + DRF
│   ├── candidatos/               # Modelos, viewsets y serializers
│   ├── transparencia_backend/    # Configuración principal
│   ├── manage.py / requirements  # Scripts de ejecución
│   └── populate_db.py            # Carga inicial de datos de ejemplo
└── App/                          # Aplicación Android (Jetpack Compose)
    ├── app/build.gradle.kts      # Configuración del módulo
    ├── app/src/main/java/...     # Código Kotlin (Compose, repos, networking)
    ├── app/src/main/assets       # Datos locales (JSON de respaldo)
    └── gradle/ ...               # Infraestructura de build
```

---

## ⚙️ Backend (Django + DRF)

### 🔑 Dependencias Clave
- Django 5.2  
- Django REST Framework  
- django-cors-headers  
- SQLite (almacenamiento liviano para pruebas)

### 🚀 Instalación y Ejecución

```bash
cd Backend
python -m venv venv
source venv/bin/activate        # macOS / Linux
venv\Scripts\activate         # Windows
pip install -r requirements.txt
python manage.py migrate
python populate_db.py           # Carga inicial de datos de ejemplo
python manage.py runserver
```

La API estará disponible en `http://127.0.0.1:8000/`.  
El emulador Android se conecta mediante `http://10.0.2.2:8000/`.

### 📦 Modelos Principales

- **Candidato**: datos generales, biografía, foto, región, profesión, visitas.  
- **HistorialCargo**: trayectoria profesional y política.  
- **Denuncia**: denuncias registradas, estado y fuente.  
- **Proyecto**: iniciativas o proyectos propuestos.  
- **Propuesta**: propuestas clasificadas por prioridad (Alta, Media, Baja).

Las relaciones están configuradas mediante `ForeignKey` con `related_name` para incluir datos relacionados en las respuestas JSON.

### 🌐 Endpoints Principales

| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/candidatos/` | Lista completa de candidatos con relaciones |
| POST | `/api/candidatos/{id}/incrementar_visita/` | Incrementa el contador de visitas |
| GET | `/api/candidatos/mas_buscados/` | Top 3 candidatos más buscados |
| GET/POST | `/api/historial_cargos/` | CRUD de historial (batch) |
| GET/POST | `/api/denuncias/` | CRUD de denuncias |
| GET/POST | `/api/proyectos/` | CRUD de proyectos |
| GET/POST | `/api/propuestas/` | CRUD de propuestas |

---

## 📱 Aplicación Android (Jetpack Compose)

### 🧩 Stack Técnico
- **Kotlin + Jetpack Compose + Material 3**
- **Retrofit + Gson** para consumo de API
- **ViewModel + StateFlow** para gestión de estado
- **Coil** para carga de imágenes remotas
- **Navigation Compose** para navegación dinámica

### 📁 Estructura del Código (`app/src/main/java/com/proyecto/app_electoral`)

- `data/network/ApiService.kt`: definición de endpoints REST.  
- `data/repository/*`: repositorios que gestionan la comunicación entre API y vistas.  
- `ui/navigation/Navigation.kt`: flujo principal de pantallas.  
- `ui/screens/*`: pantallas Compose (Home, Search, Compare, Stats, Profile).  
- `ui/components/*`: componentes reutilizables (cards, headers, listas).  
- `ui/viewmodel/*`: controladores de estado.  
- `di/Injector.kt`: configuración de Retrofit y dependencias.

### 🔄 Flujo de Datos
1. **Inicio:** descarga inicial de candidatos.  
2. **Perfil:** obtiene información completa de un candidato (biografía, denuncias, proyectos, propuestas).  
3. **Estadísticas:** genera métricas locales (porcentajes, popularidad, distribución regional).  
4. **Comparación:** muestra diferencias entre dos candidatos seleccionados.

### ▶️ Ejecución

1. Inicia el backend (`python manage.py runserver`).  
2. En Android Studio:
   ```bash
   cd App
   ./gradlew assembleDebug
   ./gradlew installDebug
   ```
3. Usa `http://10.0.2.2:8000/api/` como `BASE_URL` en `ApiService.kt`.

---

## 🧭 Fuentes de Información Pública

| Entidad | Plataforma | Tipo de Información |
|----------|-------------|--------------------|
| **JNE** | Voto Informado, Plataforma Electoral | Hojas de vida, planes de gobierno, sentencias |
| **Infogob (JNE)** | infogob.jne.gob.pe | Historial político, afiliación partidaria |
| **ONPE** | Claridad, Datos Abiertos | Financiamiento y resultados electorales |
| **Poder Judicial** | CEJ, REDAM | Expedientes judiciales, deudores alimentarios |
| **Contraloría** | DDJJ, Portal Ciudadano | Declaraciones juradas y patrimonio |
| **Congreso** | Proyectos de Ley, Asistencias | Proyectos, votaciones y participación legislativa |

---

## 🎨 Prototipo de Diseño

**Figma:** [AppElectoral - Transparencia Ciudadana](https://www.figma.com/design/vVHL6YPTLcVYzOJ4ChxqX3/AppElectoral?node-id=0-1)

Pantallas principales:
1. Inicio/Búsqueda  
2. Lista de Candidatos  
3. Detalle del Candidato  
4. Comparación  
5. Estadísticas  

---

## 🧰 Tecnologías y Herramientas

| Área | Herramienta |
|------|--------------|
| Lenguaje | Kotlin (Android), Python (Backend) |
| Frameworks | Jetpack Compose, Django, DRF |
| IDEs | Android Studio, VS Code / PyCharm |
| Base de Datos | SQLite |
| Control de Versiones | Git / GitHub |
| Diseño UI | Figma |

---

## 👥 Equipo de Desarrollo

| Rol | Nombre | Responsabilidades |
|-----|--------|-------------------|
| **Líder Técnico / Backend Django / API REST / Documentador** | **Sergio Serva** | Diseño e implementación de la API REST (modelos, endpoints, relaciones), documentación técnica y liderazgo general del equipo |
| **Arquitectura General / Backend / Documentador** | **Josue Zapata** | Estructura global del proyecto (backend + app), coordinación de integración y soporte técnico |
| **Diseñadora UI/UX** | **Yamile Ochoa** | Diseño de interfaz y experiencia de usuario, prototipo en Figma |

---

# 📋 Requerimientos Funcionales

## 🧱 Módulo Backend (Django + Django REST Framework)

### RF01 - Gestión de Candidatos
Permitir listar, registrar, editar y eliminar información de los candidatos.

### RF02 - Historial de Cargos
Registrar y mostrar la trayectoria política y profesional de cada candidato.

### RF03 - Denuncias y Antecedentes
Consultar denuncias o procesos judiciales asociados a los candidatos.

### RF04 - Proyectos e Iniciativas
Registrar y exponer los proyectos o iniciativas presentadas por los candidatos.

### RF05 - Propuestas Electorales
Mostrar las propuestas presentadas por el candidato, clasificadas por categoría y prioridad.

### RF06 - Incremento de Visitas
Incrementar el contador de visitas de un candidato cuando su perfil es consultado.

### RF07 - Candidatos Más Buscados
Permitir consultar el Top 3 de candidatos más visitados.

---

## 📱 Módulo Android (Jetpack Compose)

### RF08 - Búsqueda de Candidatos
Buscar candidatos por nombre, partido o región.

### RF09 - Visualización de Perfil Completo
Visualizar el perfil detallado de un candidato con toda su información.

### RF10 - Estadísticas Locales
Mostrar estadísticas calculadas a partir de los datos descargados (porcentaje sin denuncias, popularidad, etc.).

### RF11 - Comparación entre Candidatos
Comparar experiencia, propuestas y popularidad entre dos candidatos.

### RF12 - Acceso a Datos Offline
Mostrar datos locales de respaldo en caso de no haber conexión.

### RF13 - Interfaz Responsiva e Intuitiva
Diseño adaptable a diferentes tamaños de pantalla y dispositivos.

---

## 🧰 Módulo General / Infraestructura

### RF14 - Integración Backend–App
Comunicación entre Android y Backend mediante API REST (`http://10.0.2.2:8000/api/`).

### RF15 - Poblado Inicial de Datos
Permitir cargar datos iniciales mediante `populate_db.py`.

### RF16 - Documentación y Roles
Mantener documentación clara, completa y actualizada en el repositorio.

---

## 🚧 Próximos Pasos

- Implementar autenticación y favoritos.  
- Añadir persistencia local (modo offline).  
- Extender endpoints CRUD desde la app.  
- Agregar pruebas automáticas e integración continua.  
- Publicar versión demo en Firebase / Play Store.

---

## 🪪 Licencia y Créditos

Proyecto académico con fines de transparencia y educación cívica.  
Datos obtenidos de fuentes públicas del Estado peruano (JNE, ONPE, PJ, Contraloría, Congreso).  
Diseño UI basado en prototipo Figma original del equipo.
