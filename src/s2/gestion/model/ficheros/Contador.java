package s2.gestion.model.ficheros;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import s2.gestion.model.base.Identificable;

@Entity
@Table(name = "contador")
public @Getter @Setter class Contador extends Identificable {
    private String facturaVentas;
}
