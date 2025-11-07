from rest_framework import serializers
from datetime import date
from .models import Candidato, HistorialCargo, Denuncia, Proyecto, Propuesta


class HistorialCargoSerializer(serializers.ModelSerializer):
    periodo = serializers.SerializerMethodField() 

    class Meta:
        model = HistorialCargo
        fields = '__all__'
    def get_periodo(self, obj):
        if obj.fecha_fin:
            return f"{obj.fecha_inicio} - {obj.fecha_fin}"
        else:
            return f"{obj.fecha_inicio} - vigente"

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
    edad = serializers.SerializerMethodField() 


    class Meta:
        model = Candidato
        fields = '__all__'
    
    def get_edad(self, obj):
        if obj.nacimiento:
            today = date.today()
            return today.year - obj.nacimiento.year - (
                (today.month, today.day) < (obj.nacimiento.month, obj.nacimiento.day)
            )
        return None