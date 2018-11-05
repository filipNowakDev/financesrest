package com.filip.financesrest.components;

import com.filip.financesrest.models.EntryCategory;
import com.filip.financesrest.models.FinanceEntry;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component

public class CategoryValidator implements Validator
{
    @Override
    public boolean supports(Class<?> aClass)
    {
        return FinanceEntry.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors)
    {
        //EntryCategory category = (EntryCategory) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "NotEmpty");
    }
}
