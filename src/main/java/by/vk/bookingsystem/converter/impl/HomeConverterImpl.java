package by.vk.bookingsystem.converter.impl;

import by.vk.bookingsystem.converter.HomeConverter;
import by.vk.bookingsystem.domain.Home;
import by.vk.bookingsystem.dto.home.HomeDto;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class HomeConverterImpl implements HomeConverter {

  @Override
  public HomeDto convertToDto(final Home entity) {
    return new HomeDto(entity.getId().toHexString(), entity.getName());
  }

  @Override
  public Home convertToEntity(final HomeDto dto) {
    return new Home(new ObjectId(dto.getId()), dto.getName());
  }
}
