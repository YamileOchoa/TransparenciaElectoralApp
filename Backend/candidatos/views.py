from rest_framework import viewsets, status
from rest_framework.decorators import action
from rest_framework.response import Response
from .models import Candidato, HistorialCargo, Denuncia, Proyecto, Propuesta
from .serializers import (
    CandidatoSerializer, HistorialCargoSerializer, DenunciaSerializer, 
    ProyectoSerializer, PropuestaSerializer
)

class CandidatoViewSet(viewsets.ModelViewSet):
    queryset = Candidato.objects.all()
    serializer_class = CandidatoSerializer

    @action(detail=True, methods=['post'])
    def incrementar_visita(self, request, pk=None):
        candidato = self.get_object()
        candidato.visitas += 1
        candidato.save()
        return Response(status=status.HTTP_200_OK)

    @action(detail=False, methods=['get'])
    def mas_buscados(self, request):
        candidatos = Candidato.objects.order_by('-visitas')[:3]
        serializer = self.get_serializer(candidatos, many=True)
        return Response(serializer.data)

class HistorialCargoViewSet(viewsets.ModelViewSet):
    queryset = HistorialCargo.objects.all()
    serializer_class = HistorialCargoSerializer

class DenunciaViewSet(viewsets.ModelViewSet):
    queryset = Denuncia.objects.all()
    serializer_class = DenunciaSerializer

class ProyectoViewSet(viewsets.ModelViewSet):
    queryset = Proyecto.objects.all()
    serializer_class = ProyectoSerializer

class PropuestaViewSet(viewsets.ModelViewSet):
    queryset = Propuesta.objects.all()
    serializer_class = PropuestaSerializer
