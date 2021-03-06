package by.vk.bookingsystem.dto.user;

import java.util.Set;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * The set of data transfer object of users.
 *
 * @author Vadzim_Kavalkou
 */
@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class UserSetDto {

  @JsonProperty("users")
  @Valid
  private final Set<UserDto> users;
}
