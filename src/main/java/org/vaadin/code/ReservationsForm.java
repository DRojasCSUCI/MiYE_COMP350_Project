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
import org.graalvm.compiler.hotspot.nodes.profiling.ProfileNode;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;

public class ReservationsForm extends FormLayout {

    private TextField reservationId = new TextField("Reservation Id");
    private TextField serviceId = new TextField("Service Id");
    private ComboBox<ReservationsStatus> service = new ComboBox<>("Service");
    private DatePicker date = new DatePicker("Date");
    private TimePicker time = new TimePicker("Start Time");
    //private TimePicker duration = new TimePicker("Duration");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Reservations> binder = new Binder<>(Reservations.class);
    private ReservationsView reservationsView;
    private ReservationsService services = ReservationsService.getInstance();

    private boolean newCustomer = false;

    public ReservationsForm(ReservationsView reservationsView) throws SQLException {

//        ComboBox<Duration> stepSelector = new ComboBox<>();
//        stepSelector.setLabel("Duration");
//        stepSelector.setItems(Duration.ofMillis(500), Duration.ofSeconds(10),
//                Duration.ofMinutes(1), Duration.ofMinutes(15),
//                Duration.ofMinutes(30), Duration.ofHours(1));
//        ProfileNode timePicker = null;
//        stepSelector.setValue(timePicker.getStep());
//        stepSelector.addValueChangeListener(event -> {
//            Duration newStep = event.getValue();
//            if (newStep != null) {
//                timePicker.setStep(newStep);
//
//            }
//        });


        this.reservationsView = reservationsView;

        service.setItems(ReservationsStatus.values());

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(reservationId, serviceId, service, date, time, buttons);

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

    public void setNewCustomer(boolean newCustomer){
        this.newCustomer = newCustomer;
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

    private void save() throws SQLException {
        Reservations reservation = binder.getBean();

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
        System.out.println("Updated user: " + reservation.getReservationId() + " " + reservation.getServiceId());

//        if(newCustomer)
//        {
//            // userMger.createNewUser(con, customer);
//        }
//        else
//        {
//            userMger.updateUser(con, services);
//        }


        // close the connection
        assert con != null;
        con.close();

        services.save(reservation);
        reservationsView.updateList();
        setReservations(null);
    }

    private void delete() {
        Reservations reservation = binder.getBean();
        services.delete(reservation);
        reservationsView.updateList();
        setReservations(null);
    }

}
