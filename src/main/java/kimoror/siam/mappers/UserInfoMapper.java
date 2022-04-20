package kimoror.siam.mappers;

import kimoror.siam.models.UserInfo;
import kimoror.siam.rest.dto.UserInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    @Mappings({
          @Mapping(target = "name", source = "name"),
          @Mapping(target = "surname", source = "surname"),
          @Mapping(target = "middleName", source = "middleName"),
          @Mapping(target = "birthday", source = "birthday"),
          @Mapping(target = "address", source = "address"),
          @Mapping(target = "status", source = "status"),
          @Mapping(target = "company_id", source = "company_id"),
          @Mapping(target = "workPosition", source = "workPosition"),
          @Mapping(target = "education", source = "education"),
          @Mapping(target = "school", source = "school"),
          @Mapping(target = "university", source = "university"),
          @Mapping(target = "phoneNumber", source = "phoneNumber"),

    })
    UserInfoDto userInfoToDto(UserInfo userInfo);
}
