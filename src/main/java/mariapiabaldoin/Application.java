package mariapiabaldoin;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {


        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Geronimo Stilton", "Books", 150.00));
        products.add(new Product(2, "Biberon", "Baby", 15.00));
        products.add(new Product(3, "iPhone 16", "Electronics", 1000.00));
        products.add(new Product(4, "Hotwheels", "Boys", 10.00));
        products.add(new Product(6, "Tea Stilton", "Books", 30.00));

        Customer customer1 = new Customer(1, "Mario Bros", 1);
        Customer customer2 = new Customer(2, "Luigi Bros", 2);

        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, "Spedito", LocalDate.now().minusDays(2), LocalDate.now(), List.of(products.get(1)), customer1));


        Map<Customer, List<Order>> ordiniCustomer = orders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer));


        ordiniCustomer.forEach((customer, orderList) -> {
            System.out.println("Cliente: " + customer);
            orderList.forEach(order -> System.out.println("    Ordini: " + order));
        });


        Map<Customer, Double> totaleOrdini = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomer,
                        Collectors.summingDouble(order -> order.getProducts().stream()
                                .mapToDouble(Product::getPrice).sum())));


        totaleOrdini.forEach((customer, totalSales) -> {
            System.out.println("Cliente: " + customer + ", Totale Acquisti: " + totalSales);
        });


        List<Product> prodottiPiuCostosi = products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice).reversed())
                .limit(3)
                .toList();


        System.out.println("Prodotti più costosi:");
        prodottiPiuCostosi.forEach(product -> System.out.println(product));


        OptionalDouble mediaImporti = orders.stream()
                .mapToDouble(order -> order.getProducts().stream()
                        .mapToDouble(Product::getPrice).sum())
                .average();


        if (mediaImporti.isPresent()) {
            System.out.println("La media degli importi è: " + mediaImporti.getAsDouble());
        } else {
            System.out.println("Non è possibile calcolare la media perché la lista è vuota");
        }
    }
}
