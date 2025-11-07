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
    foto_url = serializers.SerializerMethodField()

    class Meta:
        model = Candidato
        fields = '__all__'

    def get_foto_url(self, obj):
        request = self.context.get('request')
        if obj.foto:
            return request.build_absolute_uri(obj.foto.url)
        return None