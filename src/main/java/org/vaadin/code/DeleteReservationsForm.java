package org.vaadin.code;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.sql.SQLException;

public class DeleteReservationsForm extends FormLayout {
    private TextField reservationId = new TextField("reservationId");

    private Button delete = new Button("Delete");
    private Button cancel = new Button("Cancel");

    private Binder<Reservations> binder = new Binder<>(Reservations.class);
    private ReservationsView reservationsView;
    private ReservationsService services = ReservationsService.getInstance();


    public DeleteReservationsForm(ReservationsView reservationsView) throws SQLException {
        this.reservationsView = reservationsView;

        HorizontalLayout buttons = new HorizontalLayout(delete, cancel);
        delete.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        add(reservationId, buttons);

        delete.addClickListener(event -> {
            try {
                delete();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        cancel.addClickListener(event ->{
            setReservations(null);
        });

    }

    public void setReservations(Reservations reservation) {
        binder.setBean(reservation);

        if (reservation == null) {
            setVisible(false);
        } else {
            setVisible(true);
            reservationId.focus();
        }
    }

    private void delete() throws SQLException {
        services.delete(reservationId.getValue());
        reservationsView.updateList();
        setReservations(null);
    }
}
