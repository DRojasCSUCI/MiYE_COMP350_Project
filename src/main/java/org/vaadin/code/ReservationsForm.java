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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.Calendar;
import java.util.EventListener;

public class ReservationsForm extends FormLayout {
    private TextField userId = new TextField("User ID");
    private ComboBox<ReservationsStatus> service = new ComboBox<>("Service");
    private DatePicker date = new DatePicker("Date");
    private TimePicker time = new TimePicker("Start Time");
    private ComboBox<Integer> stepSelector = new ComboBox<>("Duration");

//    private TimePicker duration = new TimePicker("Duration");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Reservations> binder = new Binder<>(Reservations.class);
    private ReservationsView reservationsView;
    private ReservationsService services = ReservationsService.getInstance();

    private boolean newReservation = false;

    public ReservationsForm(ReservationsView reservationsView) throws SQLException {

        // TimePicker timePicker = new TimePicker();

        this.reservationsView = reservationsView;

        service.setItems(ReservationsStatus.values());

        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(userId, service, date, time, stepSelector, buttons);

        binder.bindInstanceFields(this);

        save.addClickListener(event -> {
            try {
                save();
            } catch (SQLException | ParseException e) {
                e.printStackTrace();
            }
        });
        delete.addClickListener(event -> delete());
        service.addValueChangeListener(event -> durationTime());
    }


    public void setNewReservation(boolean newReservation){
        this.newReservation = newReservation;
    }

    public void setReservations(Reservations reservation) {
        binder.setBean(reservation);

        if (reservation == null) {
            setVisible(false);
        } else {
            setVisible(true);
            userId.focus();
        }
    }

    private void save() throws SQLException, ParseException
    {
        Reservations reservation = binder.getBean();

        // update database
        ReservationManager reservationManager = new ReservationManager();
        // Connection to DataBase
        ConnectionManager connMngr = new ConnectionManager();
        Connection con = null;
        try {
            con = connMngr.connect();
        } catch (Exception e) {
            System.out.println("\nERROR ON CONNECTING TO SQL DATABASE ON SAVE\n " + e);
        }

        // close the connection
        assert con != null;
        String serviceId = null;
        switch (service.getValue()){

            case MineralBath:
                serviceId = "0000001";
                break;
            case SwedishMassage:
                serviceId = "0000002";
                break;
            case ShiatsuMassage:
                serviceId = "0000003";
                break;
            case DeepTissueMassage:
                serviceId = "0000004";
                break;
            case NormalFacial:
                serviceId = "0000005";
                break;
            case CollagenFacial:
                serviceId = "0000006";
                break;
            case HotStone:
                serviceId = "0000007";
                break;
            case SugarScrub:
                serviceId = "0000008";
                break;
            case HerbalBodyWrap:
                serviceId = "0000009";
                break;
            case BotanicalMudWrap:
                serviceId = "0000010";
                break;
            default: newReservation = false;
        }

        LocalDateTime dateTime = time.getValue().atDate(date.getValue());

        reservation = reservationManager.insertReservation(con, userId.getValue(), serviceId, dateTime, stepSelector.getValue());
        if(newReservation && reservation != null){

            services.save(reservation);
            reservationsView.updateList();
        }
        con.close();
        setReservations(null);
    }

    private void durationTime(){

        switch (service.getValue()){
            case SwedishMassage:
            case ShiatsuMassage:
            case DeepTissueMassage:
            case NormalFacial:
            case CollagenFacial:
                stepSelector.setLabel("Duration");
                stepSelector.setItems(30, 60);
                break;
            case HotStone:
            case SugarScrub:
            case HerbalBodyWrap:
            case BotanicalMudWrap:
            case MineralBath:
                stepSelector.setLabel("Duration");
                stepSelector.setItems(60, 90);
                break;
            default:
                stepSelector.setLabel("Duration");
                stepSelector.setItems(30, 60, 90);
                break;
        }

    }


    private void delete() {
        Reservations reservation = binder.getBean();
        services.delete(reservation);
        reservationsView.updateList();
        setReservations(null);
    }

}
