package com.github.ivansjr.tabelafipe.main;

import com.github.ivansjr.tabelafipe.model.*;
import com.github.ivansjr.tabelafipe.service.APIConsumerService;
import com.github.ivansjr.tabelafipe.service.DataConverterService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private final Scanner scanner = new Scanner(System.in);
    private final APIConsumerService apiConsumerService = new APIConsumerService();
    private final DataConverterService dataConverterService = new DataConverterService();

    public void showMenu() {
        System.out.println("Digite um tipo de veiculo. motos / carros / caminhoes: ");
        var vehicleType = scanner.nextLine();
        String url = "https://parallelum.com.br/fipe/api/v1/" + vehicleType.toLowerCase() + "/marcas/";
        var jsonVehicleBrand = apiConsumerService.getData(url);
        VehicleBrandData[] jsonVehicleBrandData = dataConverterService.dataConverter(jsonVehicleBrand, VehicleBrandData[].class);
        List<VehicleBrandData> vehicleBrandDataList = Arrays.stream(jsonVehicleBrandData).distinct().toList();
        vehicleBrandDataList.forEach(
            vehicleBrandData -> System.out.println("Código: " + vehicleBrandData.code() + "  " + "Nome: " + vehicleBrandData.name())
        );

        var vehicleModelCode = scanner.nextLine();
        url = url + vehicleModelCode + "/modelos/";
        var jsonVehicleModel = apiConsumerService.getData(url);
        VehicleData jsonVehicleModelData = dataConverterService.dataConverter(jsonVehicleModel, VehicleData.class);
        List<VehicleModelData> vehicleModelDataList = jsonVehicleModelData.models();
        vehicleModelDataList.forEach(
            vehicleModelData -> System.out.println("Código: " + vehicleModelData.code() + "  " + "Nome: " + vehicleModelData.name())
        );

        var vehiclePartName = scanner.nextLine();
        Optional<VehicleModelData> optionalVehicleModelData = vehicleModelDataList
            .stream()
            .filter(
                vehicleModelData -> vehicleModelData.name().toLowerCase().contains(vehiclePartName.toLowerCase())
            ).findFirst();

        if (optionalVehicleModelData.isPresent()) {
            VehicleModelData vehicleModelData = optionalVehicleModelData.get();
            var jsonVehicleYears = apiConsumerService.getData(url + vehicleModelData.code() + "/anos");
            VehicleYearData[] jsonVehicleYearData = dataConverterService.dataConverter(jsonVehicleYears, VehicleYearData[].class);
            List<VehicleYearData> vehicleYearDataList = Arrays.stream(jsonVehicleYearData).toList();

            System.out.println("Resultados desse modelo de veiculo: " + vehicleModelData.name());
            System.out.println("=========================================");

            for (VehicleYearData vehicleYearData : vehicleYearDataList) {
                String yearUrl = url + vehicleModelData.code() + "/anos/" + vehicleYearData.code();
                var jsonVehicleData = apiConsumerService.getData(yearUrl);
                VehicleFullData vehicleFullData = dataConverterService.dataConverter(jsonVehicleData, VehicleFullData.class);

                System.out.println("Marca: " + vehicleFullData.brand());
                System.out.println("Preço: " + vehicleFullData.price());
                System.out.println("Ano: " + vehicleFullData.modelYear());
                System.out.println("-----------------------------------------");
            }
        } else {
            System.out.println("Vehicle not found");
        }
    }
}
