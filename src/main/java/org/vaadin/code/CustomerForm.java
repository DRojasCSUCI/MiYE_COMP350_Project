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

public class CustomerForm extends FormLayout {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private ComboBox<CustomerStatus> status = new ComboBox<>("Status");
    private DatePicker startDate = new DatePicker("Date");
    private TimePicker startTime = new TimePicker("Start Time");
    private TimePicker endTime = new TimePicker("End Time");

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    private Binder<Customer> binder = new Binder<>(Customer.class);
    private CustomerView customerView;
    private CustomerService service = CustomerService.getInstance();

    private boolean newCustomer = false;

    public CustomerForm(CustomerView customerView) throws SQLException {
        this.customerView = customerView;

        status.setItems(CustomerStatus.values());

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

    public void setNewCustomer(boolean newCustomer){ this.newCustomer = newCustomer; }

    public void setCustomer(Customer customer) {
        binder.setBean(customer);

        if (customer == null) {
            setVisible(false);
        } else {
            setVisible(true);
            firstName.focus();
        }
    }

    private void save() throws SQLException {
        Customer customer = binder.getBean();

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
        System.out.println("Updated user: " + customer.getFirstName() + " " + customer.getLastName());

//        if(newCustomer)
//        {
//            // userMger.createNewUser(con, customer);
//        }
//        else
//        {
////            userMger.updateUser(con, customer);
//        }


        // close the connection
        assert con != null;
        con.close();

        service.save(customer);
        customerView.updateList();
        setCustomer(null);
    }

    private void delete() {
        Customer customer = binder.getBean();
        service.delete(customer);
        customerView.updateList();
        setCustomer(null);
    }

}
