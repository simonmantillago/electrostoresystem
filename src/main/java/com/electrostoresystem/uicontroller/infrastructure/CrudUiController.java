package com.electrostoresystem.uicontroller.infrastructure;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.BorderLayout;
import java.awt.Dimension;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

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

import com.electrostoresystem.order.application.CreateOrderUseCase;
import com.electrostoresystem.order.application.DeleteOrderUseCase;
import com.electrostoresystem.order.application.FindOrderByIdUseCase;
import com.electrostoresystem.order.application.UpdateOrderUseCase;
import com.electrostoresystem.order.infrastructure.orderui.OrderUiController;
import com.electrostoresystem.order.domain.service.OrderService;
import com.electrostoresystem.order.infrastructure.OrderRepository;

import com.electrostoresystem.orderdetail.application.CreateOrderDetailUseCase;
import com.electrostoresystem.orderdetail.application.DeleteOrderDetailUseCase;
import com.electrostoresystem.orderdetail.application.FindOrderDetailByIdUseCase;
import com.electrostoresystem.orderdetail.application.UpdateOrderDetailUseCase;
import com.electrostoresystem.orderdetail.infrastructure.orderdetailui.OrderDetailUiController;
import com.electrostoresystem.orderdetail.domain.service.OrderDetailService;
import com.electrostoresystem.orderdetail.infrastructure.OrderDetailRepository;

import com.electrostoresystem.salestatus.application.CreateSaleStatusUseCase;
import com.electrostoresystem.salestatus.application.DeleteSaleStatusUseCase;
import com.electrostoresystem.salestatus.application.FindSaleStatusByIdUseCase;
import com.electrostoresystem.salestatus.application.UpdateSaleStatusUseCase;
import com.electrostoresystem.salestatus.infrastructure.salestatusui.SaleStatusUiController;
import com.electrostoresystem.salestatus.domain.service.SaleStatusService;
import com.electrostoresystem.salestatus.infrastructure.SaleStatusRepository;

import com.electrostoresystem.supplier.application.CreateSupplierUseCase;
import com.electrostoresystem.supplier.application.DeleteSupplierUseCase;
import com.electrostoresystem.supplier.application.FindAllSupplierUseCase;
import com.electrostoresystem.supplier.application.FindSupplierByIdUseCase;
import com.electrostoresystem.supplier.application.UpdateSupplierUseCase;
import com.electrostoresystem.supplier.infrastructure.supplierui.SupplierUiController;
import com.electrostoresystem.supplier.domain.service.SupplierService;
import com.electrostoresystem.supplier.infrastructure.SupplierRepository;

import com.electrostoresystem.paymentmethods.application.CreatePaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.application.DeletePaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.application.FindPaymentMethodsByIdUseCase;
import com.electrostoresystem.paymentmethods.application.UpdatePaymentMethodsUseCase;
import com.electrostoresystem.paymentmethods.infrastructure.paymentmethodsui.PaymentMethodsUiController;
import com.electrostoresystem.paymentmethods.domain.service.PaymentMethodsService;
import com.electrostoresystem.paymentmethods.infrastructure.PaymentMethodsRepository;

import com.electrostoresystem.product.application.CreateProductUseCase;
import com.electrostoresystem.product.application.DeleteProductUseCase;
import com.electrostoresystem.product.application.FindAllProductUseCase;
import com.electrostoresystem.product.application.FindProductByIdUseCase;
import com.electrostoresystem.product.application.UpdateProductUseCase;
import com.electrostoresystem.product.infrastructure.productui.ProductUiController;
import com.electrostoresystem.product.domain.service.ProductService;
import com.electrostoresystem.product.infrastructure.ProductRepository;

import com.electrostoresystem.region.application.CreateRegionUseCase;
import com.electrostoresystem.region.application.DeleteRegionUseCase;
import com.electrostoresystem.region.application.FindRegionByIdUseCase;
import com.electrostoresystem.region.application.UpdateRegionUseCase;
import com.electrostoresystem.region.infrastructure.regionui.RegionUiController;
import com.electrostoresystem.region.domain.service.RegionService;
import com.electrostoresystem.region.infrastructure.RegionRepository;

import com.electrostoresystem.sale.application.CreateSaleUseCase;
import com.electrostoresystem.sale.application.DeleteSaleUseCase;
import com.electrostoresystem.sale.application.FindSaleByIdUseCase;
import com.electrostoresystem.sale.application.UpdateSaleUseCase;
import com.electrostoresystem.sale.infrastructure.saleui.SaleUiController;
import com.electrostoresystem.sale.domain.service.SaleService;
import com.electrostoresystem.sale.infrastructure.SaleRepository;

import com.electrostoresystem.saledetail.application.CreateSaleDetailUseCase;
import com.electrostoresystem.saledetail.application.DeleteSaleDetailUseCase;
import com.electrostoresystem.saledetail.application.FindSaleDetailByIdUseCase;
import com.electrostoresystem.saledetail.application.UpdateSaleDetailUseCase;
import com.electrostoresystem.saledetail.infrastructure.saledetailui.SaleDetailUiController;
import com.electrostoresystem.saledetail.domain.service.SaleDetailService;
import com.electrostoresystem.saledetail.infrastructure.SaleDetailRepository;

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
        JFrame frame = new JFrame("Database Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Database Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Espacio entre botones
        
        Dimension buttonSize = new Dimension(200, 70);
        Font buttonFont = new Font("Arial", Font.BOLD, 16);
        
        // Primera columna
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        JButton btnClients = createStyledButton("Clients", buttonSize, buttonFont);
        btnClients.addActionListener(e -> {
            frame.setVisible(false);
            openClientUiController();
        });
        buttonPanel.add(btnClients, gbc);

        gbc.gridy++;
        JButton btnClientPhones = createStyledButton("Client Phones", buttonSize, buttonFont);
        btnClientPhones.addActionListener(e -> {
            frame.setVisible(false);
            openClientPhoneUiController();
        });
        buttonPanel.add(btnClientPhones, gbc);

        gbc.gridy++;
        JButton btnClientTypes = createStyledButton("Client Types", buttonSize, buttonFont);
        btnClientTypes.addActionListener(e -> {
            frame.setVisible(false);
            openClientTypeUiController();
        });
        buttonPanel.add(btnClientTypes, gbc);
        
        gbc.gridy++;
        JButton btnIdTypes = createStyledButton("Id Types", buttonSize, buttonFont);
        btnIdTypes.addActionListener(e -> {
            frame.setVisible(false);
            openIdTypeUiController();
        });
        buttonPanel.add(btnIdTypes, gbc);

        gbc.gridy++;
        JButton btnSales = createStyledButton("Sales", buttonSize, buttonFont);
        btnSales.addActionListener(e -> {
            frame.setVisible(false);
            openSaleUiController();
        });
        buttonPanel.add(btnSales, gbc);
        
        gbc.gridy++;
        JButton btnSalesDetails = createStyledButton("Sale Details", buttonSize, buttonFont);
        btnSalesDetails.addActionListener(e -> {
            frame.setVisible(false);
            openSaleDetailUiController();
        });
        buttonPanel.add(btnSalesDetails, gbc);
        
        
        
        
        // Segunda columna 
        gbc.gridx = 1;
        gbc.gridy = 0;
        JButton btnCountries = createStyledButton("Countries", buttonSize, buttonFont);
        btnCountries.addActionListener(e -> {
            frame.setVisible(false);
            openCountryUiController();
        });
        buttonPanel.add(btnCountries, gbc);
        
        gbc.gridy++;
        JButton btnRegions = createStyledButton("Regions", buttonSize, buttonFont);
        btnRegions.addActionListener(e -> {
            frame.setVisible(false);
            openRegionUiController();
        });
        buttonPanel.add(btnRegions, gbc);
        
        gbc.gridy++;
        JButton btnCities = createStyledButton("Cities", buttonSize, buttonFont);
        btnCities.addActionListener(e -> {
            frame.setVisible(false);
            openCityUiController();
        });
        buttonPanel.add(btnCities, gbc);
        
        
        
        gbc.gridy++;
        JButton btnPaymentMethods = createStyledButton("Payment Methods", buttonSize, buttonFont);
        btnPaymentMethods.addActionListener(e -> {
            frame.setVisible(false);
            openPaymentMethodsUiController();
        });
        buttonPanel.add(btnPaymentMethods, gbc);
        
        gbc.gridy++;
        JButton btnCategories = createStyledButton("Product Categories", buttonSize, buttonFont);
        btnCategories.addActionListener(e -> {
            frame.setVisible(false);
            openCategoryUiController();
        });
        buttonPanel.add(btnCategories, gbc);
        
        gbc.gridy++;
        JButton btnSaleStatus = createStyledButton("Sale Status", buttonSize, buttonFont);
        btnSaleStatus.addActionListener(e -> {
            frame.setVisible(false);
            openSaleStatusUiController();
        }); 
        buttonPanel.add(btnSaleStatus, gbc);

        gbc.gridy++;
        JButton btnOrderStatus = createStyledButton("Order Status", buttonSize, buttonFont);
        btnOrderStatus.addActionListener(e -> {
            frame.setVisible(false);
            openOrderStatusUiController();
        });
        buttonPanel.add(btnOrderStatus, gbc);

        gbc.gridy++;
        JButton btnBack = createStyledButton(" <- Go Back", buttonSize, buttonFont);
        btnBack.addActionListener(e -> {
            frame.setVisible(false);
            MainUiController.createAndShowMainUI();
        });
        buttonPanel.add(btnBack, gbc);
        
        
        // Tercera columna 
        gbc.gridx = 2;
        gbc.gridy = 0;
        JButton btnSuppliers = createStyledButton("Suppliers", buttonSize, buttonFont);
        btnSuppliers.addActionListener(e -> {
            frame.setVisible(false);
            openSupplierUiController();
        });
        buttonPanel.add(btnSuppliers, gbc);
        
        gbc.gridy++;
        JButton btnSupplierPhones = createStyledButton("Supplier Phones", buttonSize, buttonFont);
        btnSupplierPhones.addActionListener(e -> {
            frame.setVisible(false);
            openSupplierPhoneUiController();
        });
        buttonPanel.add(btnSupplierPhones, gbc);
        
        gbc.gridy++;
        JButton btnProducts = createStyledButton("Products", buttonSize, buttonFont);
        btnProducts.addActionListener(e -> {
            frame.setVisible(false);
            openProductUiController();
        });
        buttonPanel.add(btnProducts, gbc);
        
        gbc.gridy++;
        JButton btnBrands = createStyledButton("Product Brands", buttonSize, buttonFont);
        btnBrands.addActionListener(e -> {
            frame.setVisible(false);
            openBrandUiController();
        });
        buttonPanel.add(btnBrands, gbc);
        gbc.gridy++;
        JButton btnOrders = createStyledButton("Orders", buttonSize, buttonFont);
        btnOrders.addActionListener(e -> {
            frame.setVisible(false);
            openOrderUiController();
        });
        buttonPanel.add(btnOrders, gbc);

        gbc.gridy++;
        JButton btnOrderDetailss = createStyledButton("OrderDetailss", buttonSize, buttonFont);
        btnOrderDetailss.addActionListener(e -> {
            frame.setVisible(false);
            openOrderDetailUiController();
        });
        buttonPanel.add(btnOrderDetailss, gbc);
        
        
        // Agregar el panel de botones al centro del mainPanel
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.setResizable(false);
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

    private static void openSupplierUiController() {
        SupplierService supplierService = new SupplierRepository();

        CreateSupplierUseCase createSupplierUseCase = new CreateSupplierUseCase(supplierService);
        FindSupplierByIdUseCase findSupplierByIdUseCase = new FindSupplierByIdUseCase(supplierService);
        UpdateSupplierUseCase updateSupplierUseCase = new UpdateSupplierUseCase(supplierService);
        DeleteSupplierUseCase deleteSupplierUseCase = new DeleteSupplierUseCase(supplierService);
        FindAllSupplierUseCase findAllSupplierUseCase = new FindAllSupplierUseCase(supplierService);



        SupplierUiController supplierUiController = new SupplierUiController(createSupplierUseCase, findSupplierByIdUseCase, updateSupplierUseCase, deleteSupplierUseCase, findAllSupplierUseCase);
        supplierUiController.showCrudOptions();
    }
    
    private static void openProductUiController() {
        ProductService productService = new ProductRepository();

        CreateProductUseCase createProductUseCase = new CreateProductUseCase(productService);
        FindProductByIdUseCase findProductByIdUseCase = new FindProductByIdUseCase(productService);
        UpdateProductUseCase updateProductUseCase = new UpdateProductUseCase(productService);
        DeleteProductUseCase deleteProductUseCase = new DeleteProductUseCase(productService);
        FindAllProductUseCase findAllProductUseCase = new FindAllProductUseCase(productService);



        ProductUiController productUiController = new ProductUiController(createProductUseCase, findProductByIdUseCase, updateProductUseCase, deleteProductUseCase, findAllProductUseCase);
        productUiController.showCrudOptions();
    }

    private static void openOrderUiController() {
        OrderService orderService = new OrderRepository();

        CreateOrderUseCase createOrderUseCase = new CreateOrderUseCase(orderService);
        FindOrderByIdUseCase findOrderByIdUseCase = new FindOrderByIdUseCase(orderService);
        UpdateOrderUseCase updateOrderUseCase = new UpdateOrderUseCase(orderService);
        DeleteOrderUseCase deleteOrderUseCase = new DeleteOrderUseCase(orderService);

        OrderUiController orderUiController = new OrderUiController(createOrderUseCase, findOrderByIdUseCase, updateOrderUseCase, deleteOrderUseCase);
        orderUiController.showCrudOptions();
    }

    private static void openSaleUiController() {
        SaleService saleService = new SaleRepository();

        CreateSaleUseCase createSaleUseCase = new CreateSaleUseCase(saleService);
        FindSaleByIdUseCase findSaleByIdUseCase = new FindSaleByIdUseCase(saleService);
        UpdateSaleUseCase updateSaleUseCase = new UpdateSaleUseCase(saleService);
        DeleteSaleUseCase deleteSaleUseCase = new DeleteSaleUseCase(saleService);

        SaleUiController saleUiController = new SaleUiController(createSaleUseCase, findSaleByIdUseCase, updateSaleUseCase, deleteSaleUseCase);
        saleUiController.showCrudOptions();
    }

    private static void openSaleDetailUiController() {
        SaleDetailService saleDetailService = new SaleDetailRepository();

        CreateSaleDetailUseCase createSaleDetailUseCase = new CreateSaleDetailUseCase(saleDetailService);
        FindSaleDetailByIdUseCase findSaleDetailByIdUseCase = new FindSaleDetailByIdUseCase(saleDetailService);
        UpdateSaleDetailUseCase updateSaleDetailUseCase = new UpdateSaleDetailUseCase(saleDetailService);
        DeleteSaleDetailUseCase deleteSaleDetailUseCase = new DeleteSaleDetailUseCase(saleDetailService);

        SaleDetailUiController saleDetailUiController = new SaleDetailUiController(createSaleDetailUseCase, findSaleDetailByIdUseCase, updateSaleDetailUseCase, deleteSaleDetailUseCase);
        saleDetailUiController.showCrudOptions();
    }

    private static void openOrderDetailUiController() {
        OrderDetailService orderDetailService = new OrderDetailRepository();

        CreateOrderDetailUseCase createOrderDetailUseCase = new CreateOrderDetailUseCase(orderDetailService);
        FindOrderDetailByIdUseCase findOrderDetailByIdUseCase = new FindOrderDetailByIdUseCase(orderDetailService);
        UpdateOrderDetailUseCase updateOrderDetailUseCase = new UpdateOrderDetailUseCase(orderDetailService);
        DeleteOrderDetailUseCase deleteOrderDetailUseCase = new DeleteOrderDetailUseCase(orderDetailService);

        OrderDetailUiController orderDetailUiController = new OrderDetailUiController(createOrderDetailUseCase, findOrderDetailByIdUseCase, updateOrderDetailUseCase, deleteOrderDetailUseCase);
        orderDetailUiController.showCrudOptions();
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