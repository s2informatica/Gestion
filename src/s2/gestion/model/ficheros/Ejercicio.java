package s2.gestion.model.ficheros;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.NoCreate;
import org.openxava.annotations.NoModify;
import org.openxava.annotations.PreCreate;
import org.openxava.annotations.Tab;
import org.openxava.annotations.View;
import org.openxava.annotations.Views;
import org.openxava.jpa.XPersistence;
import org.openxava.util.XavaException;

import lombok.Getter;
import lombok.Setter;
import s2.gestion.model.base.Documentable;

/**
 * @author Alberto Modelo para los ejercicios contables de la empresa
 *
 */
@Entity
@Table(name = "ejercicio")
@Views({ @View(members = "nombre;nota;documentos"),
	@View(name = "newEjercicio", members = "nombre, copiarDe; copiarArticulos") })
@Tab(properties = "nombre, nota")
@NamedQueries({ @NamedQuery(name = "Ejercicio.getAll", query = "select e from Ejercicio e") })
public @Getter @Setter class Ejercicio extends Documentable {
    private String nombre;

    @Transient
    @ManyToOne
    @NoCreate
    @NoModify
    @DescriptionsList(descriptionProperties = "nombre")
    private Ejercicio copiarDe;

    @Transient
    private Boolean copiarArticulos;

    @PreCreate
    public void createEjercicio() {

	String schemaOrigen;
	boolean addDatos;
	if (copiarDe == null) {
	    schemaOrigen="public";
	    addDatos=false;
	}else{
	    schemaOrigen=copiarDe.getNombre();
	    addDatos=true;
	}
//	String sqlQuery=String.format(sql, schemaOrigen, nombre, addDatos);
	Query query = XPersistence.getManager()
		.createNativeQuery("select count(*) from public.clone_schema(?1, ?2, ?3);");
	query.setParameter(1, schemaOrigen);
	query.setParameter(2, getNombre());
	query.setParameter(3, addDatos);
	query.getResultList();
	
	
    }
    public void makeDefault(){
	ContadorGlobal contador = ContadorGlobal.getDefault();
	contador.setEjercicio(this);
	ContadorGlobal.setDefault(contador);
    }
    public static Ejercicio getDefault(){
	Ejercicio myEjercicio = ContadorGlobal.getDefault().getEjercicio();
	if (myEjercicio==null){
	    throw new XavaException("noInitEjercicio");
	}
	return myEjercicio;
    }
    
}