package org.vaadin.code;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

import java.sql.Connection;
import java.sql.SQLException;

public class ReservationsForm extends FormLayout {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private ComboBox<ReservationsStatus> status = new ComboBox<>("Status");
    private DatePicker startDate = new DatePicker("Date");
    private TimePicker startTime = new TimePicker("Start Time");
    private TimePicker endTime = new TimePicker("End Time");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Reservations> binder = new Binder<>(Reservations.class);
    private ReservationsView ReservationsView;
    private ReservationsService service = ReservationsService.getInstance();

    private boolean newReservations = false;

    public ReservationsForm(ReservationsView ReservationsView) throws SQLException {
        this.ReservationsView = ReservationsView;

        status.setItems(ReservationsStatus.values());

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(firstName, lastName, status, startDate, startTime, endTime, buttons);

        binder.bindInstanceFields(this);

        save.addClickListener(event -> {
            try {
                save();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        delete.addClickListener(event -> delete());
    }

    public void setNewReservations(boolean newReservations){ this.newReservations = newReservations; }

    public void setReservations(Reservations Reservations) {
        binder.setBean(Reservations);

        if (Reservations == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstName.focus();
        }
    }

    private void save() throws SQLException {
        Reservations Reservations = binder.getBean();

        // update database
        UserManager userMger = new UserManager();
        // Connection to DataBase
        ConnectionManager connMngr = new ConnectionManager();
        Connection con = null;
        try {
            con = connMngr.connect();
        } catch (Exception e) {
            System.out.println("\nERROR ON CONNECTING TO SQL DATABASE ON SAVE\n " + e);
        }
        // System.out.println("Updated user: " + Reservations.getFirstName() + " " + Reservations.getLastName());

        if(newReservations)
        {
            // userMger.createNewUser(con, Reservations);
        }
        else
        {
            // userMger.updateUser(con, Reservations);
        }


        // close the connection
        assert con != null;
        con.close();

        service.save(Reservations);
        ReservationsView.updateList();
        setReservations(null);
    }

    private void delete() {
        Reservations Reservations = binder.getBean();
        service.delete(Reservations);
        ReservationsView.updateList();
        setReservations(null);
    }

}
