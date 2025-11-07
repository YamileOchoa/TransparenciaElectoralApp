from django.db import models

class Candidato(models.Model):
    nombre = models.CharField(max_length=100)
    partido = models.CharField(max_length=100)
    biografia = models.TextField()
    visitas = models.PositiveIntegerField(default=0)
    foto_url = models.URLField(null=True, blank=True)  # Cambiado a URLField
    dni = models.CharField(max_length=20, null=True, blank=True)
    region = models.CharField(max_length=100, default="Desconocido")
    nacimiento = models.DateField(null=True, blank=True)
    estado = models.CharField(max_length=100, default="Activo")
    profesion = models.TextField(null=True, blank=True)
    fuente_url = models.URLField(null=True, blank=True)
    


    def __str__(self):
        return self.nombre

class HistorialCargo(models.Model):
    candidato = models.ForeignKey(Candidato, related_name='historial_cargos', on_delete=models.CASCADE)
    cargo = models.CharField(max_length=100)
    institucion = models.CharField(max_length=100)
    fecha_inicio = models.DateField()
    fecha_fin = models.DateField(null=True, blank=True)
    descripcion = models.TextField()
    fuente_url = models.URLField(null=True, blank=True)


    def __str__(self):
        return f"{self.cargo} en {self.institucion}"

class Denuncia(models.Model):
    candidato = models.ForeignKey(Candidato, related_name='denuncias', on_delete=models.CASCADE)
    titulo = models.CharField(max_length=200)
    descripcion = models.TextField()
    expediente = models.CharField(max_length=100)
    delito = models.CharField(max_length=100)
    fecha_denuncia = models.DateField()
    estado = models.CharField(max_length=100)
    fuente_url = models.URLField()

    def __str__(self):
        return self.titulo

class Proyecto(models.Model):
    class Estado(models.TextChoices):
        SIN_INICIAR = "Sin iniciar", "Sin iniciar"
        EN_EJECUCION = "En ejecución", "En ejecución"
        FINALIZADO = "Finalizado", "Finalizado"
        
    candidato = models.ForeignKey(Candidato, related_name='proyectos', on_delete=models.CASCADE)
    titulo = models.CharField(max_length=200)
    descripcion = models.TextField()
    categoria = models.CharField(max_length=100)
    fuente_url = models.URLField(null=True, blank=True)


    def __str__(self):
        return self.titulo

class Propuesta(models.Model):
    class Prioridad(models.TextChoices):
        ALTA = "Alta", "Alta"
        MEDIA = "Media", "Media"
        BAJA = "Baja", "Baja"

    candidato = models.ForeignKey(Candidato, related_name='propuestas', on_delete=models.CASCADE)
    titulo = models.CharField(max_length=200)
    descripcion = models.TextField()
    categoria = models.CharField(max_length=100)
    prioridad = models.CharField(
        max_length=50,
        choices=Prioridad.choices,
        default=Prioridad.MEDIA
    )
    fuente_url = models.URLField(null=True, blank=True)

    def __str__(self):
        return self.titulo