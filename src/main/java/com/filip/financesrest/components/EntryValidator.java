package com.filip.financesrest.components;

import com.filip.financesrest.models.FinanceEntry;
import com.filip.financesrest.models.User;
import com.filip.financesrest.repositories.EntriesRepository;
import com.filip.financesrest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class EntryValidator implements Validator
{
    @Override
    public boolean supports(Class<?> aClass)
    {
        return FinanceEntry.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors)
    {
        FinanceEntry entry = (FinanceEntry) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "NotEmpty");


        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "value", "NotEmpty");

    }
}