import os
os.environ.setdefault('DJANGO_SETTINGS_MODULE', 'transparencia_backend.settings')
import django
django.setup()

from candidatos.models import Candidato, HistorialCargo, Denuncia, Proyecto, Propuesta
from datetime import date

# Clean up existing data
Candidato.objects.all().delete()
HistorialCargo.objects.all().delete()
Denuncia.objects.all().delete()
Proyecto.objects.all().delete()
Propuesta.objects.all().delete()

# Create a candidate
candidato1 = Candidato.objects.create(
    nombre="Juan Perez",
    partido="Partido Transparente",
    region="Lima",
    biografia="Un candidato comprometido con la honestidad y el progreso.",
    experiencia=10,
    dni="12345678",
    nacimiento=date(1980, 5, 15),
    estado="Activo"
)

# Create related objects
HistorialCargo.objects.create(
    candidato=candidato1,
    cargo="Congresista",
    institucion="Congreso de la República",
    fecha_inicio=date(2016, 7, 28),
    fecha_fin=date(2021, 7, 28),
    descripcion="Participación en la comisión de fiscalización."
)

Denuncia.objects.create(
    candidato=candidato1,
    titulo="Caso de malversación de fondos",
    descripcion="Investigación en curso por presunto desvío de fondos públicos.",
    expediente="EXP-2023-001",
    delito="Peculado",
    fecha_denuncia=date(2023, 1, 20),
    estado="En investigación",
    fuente_url="http://example.com/denuncia1"
)

Proyecto.objects.create(
    candidato=candidato1,
    titulo="Ley de Transparencia Total",
    descripcion="Propuesta para hacer públicas todas las declaraciones de los funcionarios.",
    categoria="Gobernabilidad"
)

Propuesta.objects.create(
    candidato=candidato1,
    titulo="Reforma del sistema de justicia",
    descripcion="Modernización y digitalización de los procesos judiciales.",
    categoria="Justicia",
    prioridad="Alta"
)

print("Database populated with test data.")
