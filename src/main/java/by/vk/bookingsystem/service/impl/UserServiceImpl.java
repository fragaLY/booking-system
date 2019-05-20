package by.vk.bookingsystem.service.impl;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import by.vk.bookingsystem.controller.UserController;
import by.vk.bookingsystem.converter.UserConverter;
import by.vk.bookingsystem.dao.UserDao;
import by.vk.bookingsystem.domain.User;
import by.vk.bookingsystem.dto.user.UserDto;
import by.vk.bookingsystem.dto.user.UserSetDto;
import by.vk.bookingsystem.exception.ObjectNotFoundException;
import by.vk.bookingsystem.service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

/**
 * The service implementation for {@link UserDto}
 *
 * @author Vadzim_Kavalkou
 */
@Service
@PropertySources(@PropertySource("classpath:i18n/validation_errors.properties"))
public class UserServiceImpl implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

  private static final String USER_NOT_FOUND = "user.not.found";
  private static final String EMAIL_ALREADY_REGISTERED = "user.email.already.registered";
  private static final String PHONE_ALREADY_REGISTERED = "user.phone.already.registered";

  private static final String USER_NOT_FOUND_LOG = "User with id {} was not found.";
  private static final String EMAIL_ALREADY_REGISTERED_LOG =
      "The user {} uses already existed email";
  private static final String PHONE_ALREADY_REGISTERED_LOG =
      "The user {} uses already existed phone";

  private static final String PARAMETER_FORMAT = "?page=%d";
  private static final String PREVIOUS = "previous";
  private static final String NEXT = "next";

  private final UserDao userDao;
  private final UserConverter userConverter;
  private final Environment environment;

  /**
   * The constructor with parameters.
   *
   * @param userDao - {@link UserDao}
   * @param userConverter - {@link UserConverter}
   * @param environment - {@link Environment}
   */
  @Autowired
  public UserServiceImpl(
      final UserDao userDao, final UserConverter userConverter, final Environment environment) {
    this.userDao = userDao;
    this.userConverter = userConverter;
    this.environment = environment;
  }

  /**
   * Finds page of users in the system and returns it
   *
   * @param pageable {@link Pageable}
   * @return {@link UserSetDto}
   */
  @Cacheable(value = "users")
  @Override
  public UserSetDto findAllUsers(final Pageable pageable) {

    final Page<User> users = userDao.findAll(pageable);

    final Set<UserDto> userSet =
        users.stream()
            .filter(Objects::nonNull)
            .map(userConverter::convertToDto)
            .collect(Collectors.toSet());

    int page = users.getNumber();
    final int pages = users.getTotalPages();

    final UserSetDto userSetDto = new UserSetDto(userSet, page, pages);
    final String linkToOrders = linkTo(UserController.class).toString();

    userSetDto.add(
        new Link(linkToOrders.concat(String.format(PARAMETER_FORMAT, page))).withSelfRel());

    if (page > 0 && pages > page) {
      userSetDto.add(
          new Link(linkToOrders.concat(String.format(PARAMETER_FORMAT, --page))).withRel(PREVIOUS));
    }
    if (pages > ++page) {
      userSetDto.add(
          new Link(linkToOrders.concat(String.format(PARAMETER_FORMAT, ++page))).withRel(NEXT));
    }

    return userSetDto;
  }

  /**
   * Finds the user by its id.
   *
   * <p>If entity with current id is not in the system throws the {@link ObjectNotFoundException}
   *
   * @param id - the id of price. Not null.
   * @return {@link UserDto}
   * @throws ObjectNotFoundException the exception of absence any instance
   */
  @Cacheable(value = "user", key = "#id")
  @Override
  public UserDto findUserById(final String id) {

    if (!userDao.existsById(id)) {
      LOGGER.warn(USER_NOT_FOUND_LOG, id);
      throw new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND));
    }

    return userConverter.convertToDto(userDao.findUserById(id));
  }

  /**
   * Creates the user and returns its id.
   *
   * <p>If email or phone are already in use throws {@link IllegalArgumentException}
   *
   * @param user - {@link UserDto}
   * @return {@link String}
   */
  @CachePut(value = "users")
  @Override
  public String createUser(final UserDto user) {

    if (userDao.existsByEmail(user.getEmail())) {
      LOGGER.warn(EMAIL_ALREADY_REGISTERED_LOG, user);
      throw new IllegalArgumentException(environment.getProperty(EMAIL_ALREADY_REGISTERED));
    }

    if (userDao.existsByPhone(user.getPhone())) {
      LOGGER.warn(PHONE_ALREADY_REGISTERED_LOG, user);
      throw new IllegalArgumentException(environment.getProperty(PHONE_ALREADY_REGISTERED));
    }

    return userDao.save(userConverter.convertToEntity(user)).getId().toHexString();
  }

  /**
   * Enriches the user with new information from data transfer object and updates it.
   *
   * <p>If entity with current id is not in the system throws the {@link ObjectNotFoundException} *
   *
   * <p>If new email or new phone are already in use throws {@link IllegalArgumentException}
   *
   * @param user - {@link UserDto}
   * @param id - the id of user.
   */
  @Caching(put = {@CachePut(value = "users"), @CachePut(value = "user", key = "#id")})
  @Override
  public void updateUser(final UserDto user, final String id) {

    if (!userDao.existsById(id)) {
      LOGGER.warn(USER_NOT_FOUND_LOG, id);
      throw new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND));
    }

    final User userToUpdate = userDao.findUserById(id);

    final String newEmail = user.getEmail();
    final String oldEmail = userToUpdate.getEmail();

    if (!oldEmail.equalsIgnoreCase(newEmail) && userDao.existsByEmail(newEmail)) {
      LOGGER.warn(EMAIL_ALREADY_REGISTERED_LOG, user);
      throw new IllegalArgumentException(environment.getProperty(EMAIL_ALREADY_REGISTERED));
    }

    final String newPhone = user.getPhone();
    final String oldPhone = userToUpdate.getPhone();

    if (!oldPhone.equalsIgnoreCase(newPhone) && userDao.existsByPhone(newPhone)) {
      LOGGER.warn(PHONE_ALREADY_REGISTERED_LOG, user);
      throw new IllegalArgumentException(environment.getProperty(PHONE_ALREADY_REGISTERED));
    }

    userDao.save(userConverter.enrichModel(userToUpdate, user));
  }

  /**
   * Deletes user by its id.
   *
   * <p>If entity with current id is not in the system throws the {@link ObjectNotFoundException}
   *
   * @param id - the id of user
   */
  @Caching(evict = {@CacheEvict(value = "users"), @CacheEvict(value = "user", key = "#id")})
  @Override
  public void deleteUserById(final String id) {

    if (!userDao.existsById(id)) {
      LOGGER.warn(USER_NOT_FOUND_LOG, id);
      throw new ObjectNotFoundException(environment.getProperty(USER_NOT_FOUND));
    }

    userDao.deleteById(new ObjectId(id));
  }
}
