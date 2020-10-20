package cn.les.base.dto;

import cn.les.base.entity.UserDO;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class UserDTO {
    private Long id;
    private Long departId;
    private String departName;
    private String username;
    private String nickname;
    private String password;
    private Integer state;
    private List<String> roles;

    public UserDTO() { }

    public UserDTO(
            Long id,
            Long departId,
            String departName,
            String username,
            String nickname,
            Integer state) {
        this.id = id;
        this.departId = departId;
        this.departName = departName;
        this.username = username;
        this.nickname = nickname;
        this.state = state;
    }

    public static UserDTO fromUserDO(UserDO userDO) {
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(userDO, userDTO, "password");
        return userDTO;
    }

    public UserDO toUserDO() {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(this, userDO);
        return userDO;
    }
}
