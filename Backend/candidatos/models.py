from django.db import models

class Candidato(models.Model):
    nombre = models.CharField(max_length=100)
    partido = models.CharField(max_length=100)
    biografia = models.TextField()
    experiencia = models.IntegerField()
    visitas = models.PositiveIntegerField(default=0)
    foto = models.ImageField(upload_to='candidatos/', null=True, blank=True)
    dni = models.IntegerField(default=0)
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
    fecha_fin = models.DateField()
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
    candidato = models.ForeignKey(Candidato, related_name='proyectos', on_delete=models.CASCADE)
    titulo = models.CharField(max_length=200)
    descripcion = models.TextField()
    categoria = models.CharField(max_length=100)
    fuente_url = models.URLField(null=True, blank=True)


    def __str__(self):
        return self.titulo

class Propuesta(models.Model):
    candidato = models.ForeignKey(Candidato, related_name='propuestas', on_delete=models.CASCADE)
    titulo = models.CharField(max_length=200)
    descripcion = models.TextField()
    categoria = models.CharField(max_length=100)
    prioridad = models.CharField(max_length=50)
    fuente_url = models.URLField(null=True, blank=True)


    def __str__(self):
        return self.titulo
