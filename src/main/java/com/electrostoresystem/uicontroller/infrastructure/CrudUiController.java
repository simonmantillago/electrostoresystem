package com.electrostoresystem.uicontroller.infrastructure;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.electrostoresystem.clientphone.domain.service.ClientPhoneService;
import com.electrostoresystem.clientphone.infrastructure.ClientPhoneRepository;
import com.electrostoresystem.clientphone.infrastructure.clientphoneui.ClientPhoneUiController;
import com.electrostoresystem.clientphone.application.CreateClientPhoneUseCase;
import com.electrostoresystem.clientphone.application.DeleteClientPhoneUseCase;
import com.electrostoresystem.clientphone.application.FindAllClientPhoneUseCase;
import com.electrostoresystem.clientphone.application.FindClientPhoneByPhoneUseCase;
import com.electrostoresystem.clientphone.application.FindClientPhonesByClientIdUseCase;
import com.electrostoresystem.clientphone.application.UpdateClientPhoneUseCase;

import com.electrostoresystem.clienttype.domain.service.ClientTypeService;
import com.electrostoresystem.clienttype.infrastructure.ClientTypeRepository;
import com.electrostoresystem.clienttype.infrastructure.clienttypeui.ClientTypeUiController;
import com.electrostoresystem.clienttype.application.CreateClientTypeUseCase;
import com.electrostoresystem.clienttype.application.DeleteClientTypeUseCase;
import com.electrostoresystem.clienttype.application.FindClientTypeByIdUseCase;
import com.electrostoresystem.clienttype.application.UpdateClientTypeUseCase;

import com.electrostoresystem.country.infrastructure.CountryRepository;
import com.electrostoresystem.country.infrastructure.countryui.CountryUiController;
import com.electrostoresystem.country.domain.service.CountryService;
import com.electrostoresystem.country.application.CreateCountryUseCase;
import com.electrostoresystem.country.application.DeleteCountryUseCase;
import com.electrostoresystem.country.application.FindCountryByIdUseCase;
import com.electrostoresystem.country.application.UpdateCountryUseCase;

import com.electrostoresystem.supplierphone.domain.service.SupplierPhoneService;
import com.electrostoresystem.supplierphone.infrastructure.SupplierPhoneRepository;
import com.electrostoresystem.supplierphone.infrastructure.supplierphoneui.SupplierPhoneUiController;
import com.electrostoresystem.supplierphone.application.CreateSupplierPhoneUseCase;
import com.electrostoresystem.supplierphone.application.DeleteSupplierPhoneUseCase;
import com.electrostoresystem.supplierphone.application.FindAllSupplierPhoneUseCase;
import com.electrostoresystem.supplierphone.application.FindSupplierPhoneByPhoneUseCase;
import com.electrostoresystem.supplierphone.application.FindSupplierPhonesBySupplierIdUseCase;
import com.electrostoresystem.supplierphone.application.UpdateSupplierPhoneUseCase;

import com.electrostoresystem.idtype.application.CreateIdTypeUseCase;
import com.electrostoresystem.idtype.application.DeleteIdTypeUseCase;
import com.electrostoresystem.idtype.application.FindIdTypeByIdUseCase;
import com.electrostoresystem.idtype.application.UpdateIdTypeUseCase;
import com.electrostoresystem.idtype.infrastructure.idtypeui.IdTypeUiController;
import com.electrostoresystem.idtype.domain.service.IdTypeService;
import com.electrostoresystem.idtype.infrastructure.IdTypeRepository;

import com.electrostoresystem.salestatus.application.CreateSaleStatusUseCase;
import com.electrostoresystem.salestatus.application.DeleteSaleStatusUseCase;
import com.electrostoresystem.salestatus.application.FindSaleStatusByIdUseCase;
import com.electrostoresystem.salestatus.application.UpdateSaleStatusUseCase;
import com.electrostoresystem.salestatus.infrastructure.salestatusui.SaleStatusUiController;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;
import com.electrostoresystem.salestatus.infrastructure.SaleStatusRepository;

import com.electrostoresystem.paymentmethods.application.CreatePaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.application.DeletePaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.application.FindPaymentMethodsByIdUseCase;
import com.electrostoresystem.paymentmethods.application.UpdatePaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.infrastructure.paymentmethodsui.PaymentMethodsUiController;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;

import com.electrostoresystem.region.application.CreateRegionUseCase;
import com.electrostoresystem.region.application.DeleteRegionUseCase;
import com.electrostoresystem.region.application.FindRegionByIdUseCase;
import com.electrostoresystem.region.application.UpdateRegionUseCase;
import com.electrostoresystem.region.infrastructure.regionui.RegionUiController;
import com.electrostoresystem.region.domain.service.RegionService;
import com.electrostoresystem.region.infrastructure.RegionRepository;

import com.electrostoresystem.orderstatus.application.CreateOrderStatusUseCase;
import com.electrostoresystem.orderstatus.application.DeleteOrderStatusUseCase;
import com.electrostoresystem.orderstatus.application.FindOrderStatusByIdUseCase;
import com.electrostoresystem.orderstatus.application.UpdateOrderStatusUseCase;
import com.electrostoresystem.orderstatus.infrastructure.orderstatusui.OrderStatusUiController;
import com.electrostoresystem.orderstatus.domain.service.OrderStatusService;
import com.electrostoresystem.orderstatus.infrastructure.OrderStatusRepository;

import com.electrostoresystem.category.application.CreateCategoryUseCase;
import com.electrostoresystem.category.application.DeleteCategoryUseCase;
import com.electrostoresystem.category.application.FindCategoryByIdUseCase;
import com.electrostoresystem.category.application.UpdateCategoryUseCase;
import com.electrostoresystem.category.infrastructure.categoryui.CategoryUiController;
import com.electrostoresystem.category.domain.service.CategoryService;
import com.electrostoresystem.category.infrastructure.CategoryRepository;

import com.electrostoresystem.city.application.CreateCityUseCase;
import com.electrostoresystem.city.application.DeleteCityUseCase;
import com.electrostoresystem.city.application.FindCityByIdUseCase;
import com.electrostoresystem.city.application.UpdateCityUseCase;
import com.electrostoresystem.city.infrastructure.cityui.CityUiController;
import com.electrostoresystem.city.domain.service.CityService;
import com.electrostoresystem.city.infrastructure.CityRepository;

import com.electrostoresystem.client.application.CreateClientUseCase;
import com.electrostoresystem.client.application.DeleteClientUseCase;
import com.electrostoresystem.client.application.FindAllClientUseCase;
import com.electrostoresystem.client.application.FindClientByIdUseCase;
import com.electrostoresystem.client.application.UpdateClientUseCase;
import com.electrostoresystem.client.infrastructure.clientui.ClientUiController;
import com.electrostoresystem.client.domain.service.ClientService;
import com.electrostoresystem.client.infrastructure.ClientRepository;

import com.electrostoresystem.brand.application.CreateBrandUseCase;
import com.electrostoresystem.brand.application.DeleteBrandUseCase;
import com.electrostoresystem.brand.application.FindBrandByIdUseCase;
import com.electrostoresystem.brand.application.UpdateBrandUseCase;
import com.electrostoresystem.brand.infrastructure.brandui.BrandUiController;
import com.electrostoresystem.brand.domain.service.BrandService;
import com.electrostoresystem.brand.infrastructure.BrandRepository;
public class CrudUiController{
    
    public CrudUiController() {
        
    }
    
    public static void createAndShowMainUI() {
        JFrame frame = new JFrame("Survey Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 800);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Dimension buttonSize = new Dimension(250, 50);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        JButton btnClientPhones = createStyledButton("Client Phones", buttonSize, buttonFont);
        btnClientPhones.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnClientPhones.addActionListener(e -> {
            frame.setVisible(false);
            openClientPhoneUiController();
        });

        buttonPanel.add(btnClientPhones);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnSupplierPhones = createStyledButton("Supplier Phones", buttonSize, buttonFont);
        btnSupplierPhones.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSupplierPhones.addActionListener(e -> {
            frame.setVisible(false);
            openSupplierPhoneUiController();
        });

        buttonPanel.add(btnSupplierPhones);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnCountries = createStyledButton("Countries", buttonSize, buttonFont);
        btnCountries.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCountries.addActionListener(e -> {
            frame.setVisible(false);
            openCountryUiController();
        });

        buttonPanel.add(btnCountries);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnClientTypes = createStyledButton("Client Types", buttonSize, buttonFont);
        btnClientTypes.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnClientTypes.addActionListener(e -> {
            frame.setVisible(false);
            openClientTypeUiController();
        });
        
        buttonPanel.add(btnClientTypes);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JButton btnIdTypes = createStyledButton("Id Types", buttonSize, buttonFont);
        btnIdTypes.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIdTypes.addActionListener(e -> {
            frame.setVisible(false);
            openIdTypeUiController();
        });
        
        buttonPanel.add(btnIdTypes);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JButton btnOrderStatus = createStyledButton("Order Status", buttonSize, buttonFont);
        btnOrderStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOrderStatus.addActionListener(e -> {
            frame.setVisible(false);
            openOrderStatusUiController();
        });

        buttonPanel.add(btnOrderStatus);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnSaleStatus = createStyledButton("Sale Status", buttonSize, buttonFont);
        btnSaleStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSaleStatus.addActionListener(e -> {
            frame.setVisible(false);
            openSaleStatusUiController();
        });

        buttonPanel.add(btnSaleStatus);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnPaymentMethods = createStyledButton("Payment Methods", buttonSize, buttonFont);
        btnPaymentMethods.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPaymentMethods.addActionListener(e -> {
            frame.setVisible(false);
            openPaymentMethodsUiController();
        });

        buttonPanel.add(btnPaymentMethods);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnCategories = createStyledButton("Product Categories", buttonSize, buttonFont);
        btnCategories.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCategories.addActionListener(e -> {
            frame.setVisible(false);
            openCategoryUiController();
        });

        buttonPanel.add(btnCategories);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnBrands = createStyledButton("Product Brands", buttonSize, buttonFont);
        btnBrands.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBrands.addActionListener(e -> {
            frame.setVisible(false);
            openBrandUiController();
        });

        buttonPanel.add(btnBrands);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnRegions = createStyledButton("Regions", buttonSize, buttonFont);
        btnRegions.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRegions.addActionListener(e -> {
            frame.setVisible(false);
            openRegionUiController();
        });

        buttonPanel.add(btnRegions);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        JButton btnCities = createStyledButton("Cities", buttonSize, buttonFont);
        btnCities.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnCities.addActionListener(e -> {
            frame.setVisible(false);
            openCityUiController();
        });

        buttonPanel.add(btnCities);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton btnClients = createStyledButton("Clients", buttonSize, buttonFont);
        btnClients.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnClients.addActionListener(e -> {
            frame.setVisible(false);
            openClientUiController();
        });

        buttonPanel.add(btnClients);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        

        
        mainPanel.add(buttonPanel);
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private static void openClientPhoneUiController() {
        ClientPhoneService clientPhoneService = new ClientPhoneRepository();

        CreateClientPhoneUseCase createClientPhoneUseCase = new CreateClientPhoneUseCase(clientPhoneService);
        FindClientPhoneByPhoneUseCase findClientPhoneByPhoneUseCase = new FindClientPhoneByPhoneUseCase(clientPhoneService);
        UpdateClientPhoneUseCase updateClientPhoneUseCase = new UpdateClientPhoneUseCase(clientPhoneService);
        DeleteClientPhoneUseCase deleteClientPhoneUseCase = new DeleteClientPhoneUseCase(clientPhoneService);
        FindClientPhonesByClientIdUseCase findClientPhonesByClientIdUseCase = new FindClientPhonesByClientIdUseCase(clientPhoneService);
        FindAllClientPhoneUseCase findAllClientPhoneUseCase = new FindAllClientPhoneUseCase(clientPhoneService);



        ClientPhoneUiController clientPhoneUiController = new ClientPhoneUiController(createClientPhoneUseCase, findClientPhonesByClientIdUseCase, updateClientPhoneUseCase, deleteClientPhoneUseCase, findAllClientPhoneUseCase, findClientPhoneByPhoneUseCase);
        clientPhoneUiController.showCrudOptions();
    }

    private static void openSupplierPhoneUiController() {
        SupplierPhoneService supplierPhoneService = new SupplierPhoneRepository();

        CreateSupplierPhoneUseCase createSupplierPhoneUseCase = new CreateSupplierPhoneUseCase(supplierPhoneService);
        FindSupplierPhoneByPhoneUseCase findSupplierPhoneByPhoneUseCase = new FindSupplierPhoneByPhoneUseCase(supplierPhoneService);
        UpdateSupplierPhoneUseCase updateSupplierPhoneUseCase = new UpdateSupplierPhoneUseCase(supplierPhoneService);
        DeleteSupplierPhoneUseCase deleteSupplierPhoneUseCase = new DeleteSupplierPhoneUseCase(supplierPhoneService);
        FindSupplierPhonesBySupplierIdUseCase findSupplierPhonesBySupplierIdUseCase = new FindSupplierPhonesBySupplierIdUseCase(supplierPhoneService);
        FindAllSupplierPhoneUseCase findAllSupplierPhoneUseCase = new FindAllSupplierPhoneUseCase(supplierPhoneService);



        SupplierPhoneUiController supplierPhoneUiController = new SupplierPhoneUiController(createSupplierPhoneUseCase, findSupplierPhonesBySupplierIdUseCase, updateSupplierPhoneUseCase, deleteSupplierPhoneUseCase, findAllSupplierPhoneUseCase, findSupplierPhoneByPhoneUseCase);
        supplierPhoneUiController.showCrudOptions();
    }

    private static void openCountryUiController() {
        CountryService countryService = new CountryRepository();

        CreateCountryUseCase createCountryUseCase = new CreateCountryUseCase(countryService);
        FindCountryByIdUseCase findCountryByIdUseCase = new FindCountryByIdUseCase(countryService);
        UpdateCountryUseCase updateCountryUseCase = new UpdateCountryUseCase(countryService);
        DeleteCountryUseCase deleteCountryUseCase = new DeleteCountryUseCase(countryService);

        CountryUiController countryUiController = new CountryUiController(createCountryUseCase, findCountryByIdUseCase, updateCountryUseCase, deleteCountryUseCase);
        countryUiController.showCrudOptions();
    }

    private static void openClientTypeUiController() {
        ClientTypeService clientTypeService = new ClientTypeRepository();

        CreateClientTypeUseCase createClientTypeUseCase = new CreateClientTypeUseCase(clientTypeService);
        FindClientTypeByIdUseCase findClientTypeByIdUseCase = new FindClientTypeByIdUseCase(clientTypeService);
        UpdateClientTypeUseCase updateClientTypeUseCase = new UpdateClientTypeUseCase(clientTypeService);
        DeleteClientTypeUseCase deleteClientTypeUseCase = new DeleteClientTypeUseCase(clientTypeService);

        ClientTypeUiController clientTypeUiController = new ClientTypeUiController(createClientTypeUseCase, findClientTypeByIdUseCase, updateClientTypeUseCase, deleteClientTypeUseCase);
        clientTypeUiController.showCrudOptions();
    }
    
    private static void openIdTypeUiController() {
        IdTypeService idTypeService = new IdTypeRepository();

        CreateIdTypeUseCase createIdTypeUseCase = new CreateIdTypeUseCase(idTypeService);
        FindIdTypeByIdUseCase findIdTypeByIdUseCase = new FindIdTypeByIdUseCase(idTypeService);
        UpdateIdTypeUseCase updateIdTypeUseCase = new UpdateIdTypeUseCase(idTypeService);
        DeleteIdTypeUseCase deleteIdTypeUseCase = new DeleteIdTypeUseCase(idTypeService);

        IdTypeUiController idTypeUiController = new IdTypeUiController(createIdTypeUseCase, findIdTypeByIdUseCase, updateIdTypeUseCase, deleteIdTypeUseCase);
        idTypeUiController.showCrudOptions();
    }

    private static void openSaleStatusUiController() {
        SaleStatusService saleStatusService = new SaleStatusRepository();

        CreateSaleStatusUseCase createSaleStatusUseCase = new CreateSaleStatusUseCase(saleStatusService);
        FindSaleStatusByIdUseCase findSaleStatusByIdUseCase = new FindSaleStatusByIdUseCase(saleStatusService);
        UpdateSaleStatusUseCase updateSaleStatusUseCase = new UpdateSaleStatusUseCase(saleStatusService);
        DeleteSaleStatusUseCase deleteSaleStatusUseCase = new DeleteSaleStatusUseCase(saleStatusService);

        SaleStatusUiController saleStatusUiController = new SaleStatusUiController(createSaleStatusUseCase, findSaleStatusByIdUseCase, updateSaleStatusUseCase, deleteSaleStatusUseCase);
        saleStatusUiController.showCrudOptions();
    }

    private static void openPaymentMethodsUiController() {
        PaymentMethodsService paymentMethodsService = new PaymentMethodsRepository();

        CreatePaymentMethodsUseCase createPaymentMethodsUseCase = new CreatePaymentMethodsUseCase(paymentMethodsService);
        FindPaymentMethodsByIdUseCase findPaymentMethodsByIdUseCase = new FindPaymentMethodsByIdUseCase(paymentMethodsService);
        UpdatePaymentMethodsUseCase updatePaymentMethodsUseCase = new UpdatePaymentMethodsUseCase(paymentMethodsService);
        DeletePaymentMethodsUseCase deletePaymentMethodsUseCase = new DeletePaymentMethodsUseCase(paymentMethodsService);

        PaymentMethodsUiController paymentMethodsUiController = new PaymentMethodsUiController(createPaymentMethodsUseCase, findPaymentMethodsByIdUseCase, updatePaymentMethodsUseCase, deletePaymentMethodsUseCase);
        paymentMethodsUiController.showCrudOptions();
    }

    private static void openOrderStatusUiController() {
        OrderStatusService orderStatusService = new OrderStatusRepository();

        CreateOrderStatusUseCase createOrderStatusUseCase = new CreateOrderStatusUseCase(orderStatusService);
        FindOrderStatusByIdUseCase findOrderStatusByIdUseCase = new FindOrderStatusByIdUseCase(orderStatusService);
        UpdateOrderStatusUseCase updateOrderStatusUseCase = new UpdateOrderStatusUseCase(orderStatusService);
        DeleteOrderStatusUseCase deleteOrderStatusUseCase = new DeleteOrderStatusUseCase(orderStatusService);

        OrderStatusUiController orderStatusUiController = new OrderStatusUiController(createOrderStatusUseCase, findOrderStatusByIdUseCase, updateOrderStatusUseCase, deleteOrderStatusUseCase);
        orderStatusUiController.showCrudOptions();
    }

    private static void openCategoryUiController() {
        CategoryService categoryService = new CategoryRepository();

        CreateCategoryUseCase createCategoryUseCase = new CreateCategoryUseCase(categoryService);
        FindCategoryByIdUseCase findCategoryByIdUseCase = new FindCategoryByIdUseCase(categoryService);
        UpdateCategoryUseCase updateCategoryUseCase = new UpdateCategoryUseCase(categoryService);
        DeleteCategoryUseCase deleteCategoryUseCase = new DeleteCategoryUseCase(categoryService);

        CategoryUiController categoryUiController = new CategoryUiController(createCategoryUseCase, findCategoryByIdUseCase, updateCategoryUseCase, deleteCategoryUseCase);
        categoryUiController.showCrudOptions();
    }

    private static void openBrandUiController() {
        BrandService brandService = new BrandRepository();

        CreateBrandUseCase createBrandUseCase = new CreateBrandUseCase(brandService);
        FindBrandByIdUseCase findBrandByIdUseCase = new FindBrandByIdUseCase(brandService);
        UpdateBrandUseCase updateBrandUseCase = new UpdateBrandUseCase(brandService);
        DeleteBrandUseCase deleteBrandUseCase = new DeleteBrandUseCase(brandService);

        BrandUiController brandUiController = new BrandUiController(createBrandUseCase, findBrandByIdUseCase, updateBrandUseCase, deleteBrandUseCase);
        brandUiController.showCrudOptions();
    }

    private static void openRegionUiController() {
        RegionService regionService = new RegionRepository();

        CreateRegionUseCase createRegionUseCase = new CreateRegionUseCase(regionService);
        FindRegionByIdUseCase findRegionByIdUseCase = new FindRegionByIdUseCase(regionService);
        UpdateRegionUseCase updateRegionUseCase = new UpdateRegionUseCase(regionService);
        DeleteRegionUseCase deleteRegionUseCase = new DeleteRegionUseCase(regionService);

        RegionUiController regionUiController = new RegionUiController(createRegionUseCase, findRegionByIdUseCase, updateRegionUseCase, deleteRegionUseCase);
        regionUiController.showCrudOptions();
    }
    
    private static void openCityUiController() {
        CityService cityService = new CityRepository();

        CreateCityUseCase createCityUseCase = new CreateCityUseCase(cityService);
        FindCityByIdUseCase findCityByIdUseCase = new FindCityByIdUseCase(cityService);
        UpdateCityUseCase updateCityUseCase = new UpdateCityUseCase(cityService);
        DeleteCityUseCase deleteCityUseCase = new DeleteCityUseCase(cityService);

        CityUiController cityUiController = new CityUiController(createCityUseCase, findCityByIdUseCase, updateCityUseCase, deleteCityUseCase);
        cityUiController.showCrudOptions();
    }

    private static void openClientUiController() {
        ClientService clientService = new ClientRepository();

        CreateClientUseCase createClientUseCase = new CreateClientUseCase(clientService);
        FindClientByIdUseCase findClientByIdUseCase = new FindClientByIdUseCase(clientService);
        UpdateClientUseCase updateClientUseCase = new UpdateClientUseCase(clientService);
        DeleteClientUseCase deleteClientUseCase = new DeleteClientUseCase(clientService);
        FindAllClientUseCase findAllClientUseCase = new FindAllClientUseCase(clientService);



        ClientUiController clientUiController = new ClientUiController(createClientUseCase, findClientByIdUseCase, updateClientUseCase, deleteClientUseCase, findAllClientUseCase);
        clientUiController.showCrudOptions();
    }

    
    private static JButton createStyledButton(String text, Dimension size, Font font) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFont(font);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        return button;
    }
}