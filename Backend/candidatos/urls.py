from django.urls import path, include
from rest_framework.routers import DefaultRouter
from .views import (
    CandidatoViewSet, HistorialCargoViewSet, DenunciaViewSet, 
    ProyectoViewSet, PropuestaViewSet
)

router = DefaultRouter()
router.register(r'candidatos', CandidatoViewSet)
router.register(r'historial_cargos', HistorialCargoViewSet)
router.register(r'denuncias', DenunciaViewSet)
router.register(r'proyectos', ProyectoViewSet)
router.register(r'propuestas', PropuestaViewSet)

urlpatterns = [
    path('', include(router.urls)),
]