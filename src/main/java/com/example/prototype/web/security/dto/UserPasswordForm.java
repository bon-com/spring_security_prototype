package com.example.prototype.web.security.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.example.prototype.base.common.constants.Constants;
import com.example.prototype.web.common.validator.FieldsMatch;

import lombok.Data;

/**
 * パスワード入力フォーム
 */
@Data
@FieldsMatch(field = "newPassword", confirmField = "confirmPassword", errorField = "confirmPassword", message = Constants.ERR_MSG_PASSWORDS_NOT_MATCH)
public class UserPasswordForm {
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{8,16}$", message = "パスワードは半角英数字8〜16文字で入力してください")
    private String newPassword;

    @NotNull
    private String confirmPassword;
}
