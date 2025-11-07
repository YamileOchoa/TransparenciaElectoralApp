from django.contrib import admin
from .models import Candidato, HistorialCargo, Denuncia, Proyecto, Propuesta

admin.site.register(Candidato)
admin.site.register(HistorialCargo)
admin.site.register(Denuncia)
admin.site.register(Proyecto)
admin.site.register(Propuesta)
