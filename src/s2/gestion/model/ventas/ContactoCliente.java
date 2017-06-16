package s2.gestion.model.ventas;

import javax.persistence.*;

import org.openxava.annotations.NoFrame;
import org.openxava.annotations.View;

import lombok.Getter;
import lombok.Setter;
import s2.gestion.model.base.Contacto;
import s2.gestion.model.base.Documentable;

/**
 * @author Alberto
 * Modelo para los contactos de los clientes
 *
 */
@Entity
@Table(name = "contacto_cliente")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="tipo_entidad")
@View(members="contacto;documentos;nota")
public @Getter @Setter class ContactoCliente extends Documentable{
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(foreignKey=@ForeignKey(name="fk_cliente"))
    private Cliente cliente;
    
    @Embedded
    @NoFrame
    private Contacto contacto;

}
