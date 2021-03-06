package by.vk.bookingsystem.service;

import by.vk.bookingsystem.dto.order.OrderDto;
import by.vk.bookingsystem.dto.order.OrderSetDto;

/**
 * The service for {@link OrderDto}
 *
 * @author Vadzim_Kavalkou
 */
public interface OrderService {

  /**
   * Finds all orders in the system and returns them.
   *
   * @return {@link OrderSetDto}
   */
  OrderSetDto findAllOrders();

  /**
   * Finds the order by its id.
   *
   * @param id - the id of order
   * @return {@link OrderDto}
   */
  OrderDto findOrderById(String id);

  /**
   * Creates the order and returns its id
   *
   * @param dto - {@link OrderDto}
   * @return {@link String}
   */
  String createOrder(OrderDto dto);

  /**
   * Enriches the order with new information from data transfer object and updates it.
   *
   * @param dto - {@link OrderDto}
   * @param id - the id of order.
   */
  void updateOrder(OrderDto dto, String id);

  /**
   * Deletes order by its id.
   *
   * @param id - the id of order
   */
  void deleteOrderById(String id);
}
