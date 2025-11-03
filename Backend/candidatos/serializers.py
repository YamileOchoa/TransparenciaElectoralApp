from rest_framework import serializers
from .models import Candidato, HistorialCargo, Denuncia, Proyecto, Propuesta

class HistorialCargoSerializer(serializers.ModelSerializer):
    class Meta:
        model = HistorialCargo
        fields = '__all__'

class DenunciaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Denuncia
        fields = '__all__'

class ProyectoSerializer(serializers.ModelSerializer):
    class Meta:
        model = Proyecto
        fields = '__all__'

class PropuestaSerializer(serializers.ModelSerializer):
    class Meta:
        model = Propuesta
        fields = '__all__'

class CandidatoSerializer(serializers.ModelSerializer):
    historial_cargos = HistorialCargoSerializer(many=True, read_only=True)
    denuncias = DenunciaSerializer(many=True, read_only=True)
    proyectos = ProyectoSerializer(many=True, read_only=True)
    propuestas = PropuestaSerializer(many=True, read_only=True)

    class Meta:
        model = Candidato
        fields = (
            'id', 'nombre', 'partido', 'region', 'biografia', 'experiencia', 
            'dni', 'nacimiento', 'estado', 'visitas', 'foto', 
            'historial_cargos', 'denuncias', 'proyectos', 'propuestas'
        )