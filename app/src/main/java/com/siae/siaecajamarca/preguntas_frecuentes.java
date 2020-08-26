package com.siae.siaecajamarca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.siae.siaecajamarca.R.layout.activity_preguntas;

public class preguntas_frecuentes extends Fragment {

    private ExpandableListView expLV;
    private ExpLVAdapter adapter;
    private ArrayList<String> Listcategoria;
    private Map<String,ArrayList<String>> mapchild;
    private View rootView;

    public preguntas_frecuentes(){

    }
    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
  //      setContentView(R.layout.activity_preguntas);
        rootView = inflater.inflate(activity_preguntas, container, false);

        expLV = (ExpandableListView) rootView.findViewById(R.id.expandableListView);
        Listcategoria= new ArrayList<>();
        mapchild= new HashMap<>();
        cargardatos();
        return rootView;
    }


    private void cargardatos(){

        ArrayList<String> p1= new ArrayList<>();
        ArrayList<String> p2= new ArrayList<>();
        ArrayList<String> p3= new ArrayList<>();
        ArrayList<String> p4= new ArrayList<>();
        ArrayList<String> p5= new ArrayList<>();
        ArrayList<String> p6= new ArrayList<>();
        ArrayList<String> p7= new ArrayList<>();
        ArrayList<String> p8= new ArrayList<>();
        ArrayList<String> p9= new ArrayList<>();
        ArrayList<String> p10= new ArrayList<>();



        Listcategoria.add("¿Qué es el SIAE?");
        Listcategoria.add("¿Cuál es la función del SIAE?");
        Listcategoria.add("¿Cómo me registro en el SIAE?");
        Listcategoria.add("¿Qué acciones puedo realizar con el SIAE?");
        Listcategoria.add("¿Cuál es la diferencia entre un SOS y una incidencia?");
        Listcategoria.add("¿Puedo adjuntar archivos multimedia a una incidencia?");
        Listcategoria.add("Registre una incidencia en mi localidad. ¿Puedo visualizarla aquí?");
        Listcategoria.add("¿Cómo puedo contactarme con la municipalidad?");
        Listcategoria.add("¿Dónde puedo actualizar mis datos personales?");
        Listcategoria.add("¿Cómo puedo recuperar mi contraseña?");

        p1.add("Es un medio de comunicación rápido y efectivo entre el ciudadano y la municipalidad, el cual permite que los ciudadanos reporten incidencias desde su celular hacia el centro de monitoreo y control de la municipalidad.");
        p2.add("Orientar y transmitir de forma inmediata la información de una incidencia para facilitar el proceso de atención de las mismas por parte de las autoridades.");
        p3.add("En el aplicativo movil de Seguridad ciudadana puede Reportar incidentes con una breve descripción, audio y/o video, tambien se enviar alerta rapidas con el boton SOS, asi mismo llamar a la central de serenazgo, ambulancias y cuerpo de bomberos");
        p4.add("Puedes reportar incidencias que están ocurriendo en tu localidad, ya sea para tu persona, familiar o vecino, la incidencia puede ser descrita literalmente y/o contener\n" +
                "evidencias multimedia como audio y/o video");
        p5.add(" El SOS es una alerta rápida con caracter de urgencia y se encarga de notificar a las autoridades que un evento de emergencia se está sucitanto, en este escenario se envían\n" +
                "tus datos personales(Nombres y apellidos) y la ubicación de donde reportaste la incidencia. A diferencia del SOS la incidencia contempla mayor información(adicional a tus datos personales y ubicación) y puede contener una descripción literal de la incidencia con evidencias multimedia como audio y/o video.");
        p6.add("Si, puedes adjuntar como evidencia un audio y/o video que den a conocer la incidencia a reportar.");

        p7.add("Si, puede visualizar las incidencias registradas desde su usuario y ver el esta en el que se encuentra.");
        p8.add("En el aplicativo SIAE -  Cajamarca tiene la funcionalidad de contacto directo mediante una la ventana de sugerencias y/o llamando a los números que se encuentran en la misma ventana");
        p9.add("Lo datos personales se pueden actualizar en la opción del menu 'Mis datos' con lo cual te permitira actulizar los nombres y apellidos.");
        p10.add("Existe dos formas de recuperar las contraseñas, en la ventana de inicio");

        mapchild.put(Listcategoria.get(0),p1);
        mapchild.put(Listcategoria.get(1),p2);
        mapchild.put(Listcategoria.get(2),p3);
        mapchild.put(Listcategoria.get(3),p4);
        mapchild.put(Listcategoria.get(4),p5);
        mapchild.put(Listcategoria.get(5),p6);
        mapchild.put(Listcategoria.get(6),p7);
        mapchild.put(Listcategoria.get(7),p8);
        mapchild.put(Listcategoria.get(8),p9);
        mapchild.put(Listcategoria.get(9),p10);

        adapter = new ExpLVAdapter(Listcategoria, mapchild, getContext());

        expLV.setAdapter(adapter);

        expLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getContext(), "sdhsdhsh", Toast.LENGTH_SHORT).show();
            }
        });


    }

}
