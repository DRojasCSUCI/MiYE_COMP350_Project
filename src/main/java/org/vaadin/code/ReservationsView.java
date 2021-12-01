package org.vaadin.code;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.sql.Connection;
import java.sql.SQLException;

@Route(value="reservations", layout = MainLayout.class)
@PageTitle("Reservations Page")
public class ReservationsView extends VerticalLayout {

    private ReservationsService service = ReservationsService.getInstance();
    private Grid<Reservations> grid = new Grid<>(Reservations.class);
    private TextField filterText = new TextField();
    private ReservationsForm form = new ReservationsForm(this);

    public ReservationsView() throws SQLException {
        filterText.setPlaceholder("Filter reservations...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.EAGER);
        filterText.addValueChangeListener(e -> updateList());

        Button addReservationBtn = new Button("Add new reservation");
        addReservationBtn.addClickListener(e -> {
            grid.asSingleSelect().clear();
            form.setReservations(new Reservations());
        });

        HorizontalLayout toolbar = new HorizontalLayout(filterText,
                addReservationBtn);

        grid.setColumns("reservationId", "userId", "firstName", "lastName", "status", "date", "time", "duration", "cost");

        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();
        grid.setSizeFull();

        add(toolbar, mainContent);

        setSizeFull();

        updateList();
        form.setReservations(null);

//        grid.asSingleSelect().addValueChangeListener(event ->
//                form.setReservations(grid.asSingleSelect().getValue()));
    }

    public void updateList() {
        grid.setItems(service.findAll(filterText.getValue()));
    }

}
