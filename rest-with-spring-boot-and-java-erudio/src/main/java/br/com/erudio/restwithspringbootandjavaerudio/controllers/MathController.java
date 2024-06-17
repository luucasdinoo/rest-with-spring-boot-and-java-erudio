package br.com.erudio.restwithspringbootandjavaerudio.controllers;

import br.com.erudio.restwithspringbootandjavaerudio.exceptions.UnsupportedMethodOperationException;
import br.com.erudio.restwithspringbootandjavaerudio.util.MathUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MathController {

    @GetMapping(value = "sum/{number1}/{number2}")
    public Double sum(@PathVariable String number1, @PathVariable String number2) throws UnsupportedMethodOperationException {

        if (!MathUtils.isNumeric(number1) || !MathUtils.isNumeric(number2))
            throw new UnsupportedMethodOperationException("Please provide a valid number");

        return MathUtils.convertToDouble(number1) + MathUtils.convertToDouble(number2);
    }

    @GetMapping(value = "sub/{number1}/{number2}")
    public Double sub(@PathVariable String number1, @PathVariable String number2) throws UnsupportedMethodOperationException {

        if (!MathUtils.isNumeric(number1) || !MathUtils.isNumeric(number2))
            throw new UnsupportedMethodOperationException("Please provide a valid number");

        return MathUtils.convertToDouble(number1) - MathUtils.convertToDouble(number2);
    }

    @GetMapping(value = "mult/{number1}/{number2}")
    public Double mult(@PathVariable String number1, @PathVariable String number2) throws UnsupportedMethodOperationException {

        if (!MathUtils.isNumeric(number1) || !MathUtils.isNumeric(number2))
            throw new UnsupportedMethodOperationException("Please provide a valid number");

        return MathUtils.convertToDouble(number1) * MathUtils.convertToDouble(number2);
    }

    @GetMapping(value = "div/{number1}/{number2}")
    public Double div(@PathVariable String number1, @PathVariable String number2) throws UnsupportedMethodOperationException {

        if (!MathUtils.isNumeric(number1) || !MathUtils.isNumeric(number2))
            throw new UnsupportedMethodOperationException("Please provide a valid number");

        return MathUtils.convertToDouble(number1) / MathUtils.convertToDouble(number2);
    }

    @GetMapping(value = "sqrt/{number1}")
    public Double sqrt(@PathVariable String number1) throws UnsupportedMethodOperationException {

        if (!MathUtils.isNumeric(number1))
            throw new UnsupportedMethodOperationException("Please provide a valid number");

        return Math.sqrt(MathUtils.convertToDouble(number1));
    }

    @GetMapping(value = "pow/{number1}/{number2}")
    public Double pow(@PathVariable String number1, @PathVariable String number2) throws UnsupportedMethodOperationException {

        if (!MathUtils.isNumeric(number1) || !MathUtils.isNumeric(number2))
            throw new UnsupportedMethodOperationException("Please provide a valid number");

        return Math.pow(MathUtils.convertToDouble(number1), MathUtils.convertToDouble(number2));
    }

    @GetMapping(value = "mean/{number1}/{number2}")
    public Double mean(@PathVariable String number1, @PathVariable String number2) throws UnsupportedMethodOperationException {

        if (!MathUtils.isNumeric(number1) || !MathUtils.isNumeric(number2))
            throw new UnsupportedMethodOperationException("Please provide a valid number");

        return (MathUtils.convertToDouble(number1) + MathUtils.convertToDouble(number2)) / 2;
    }

}
