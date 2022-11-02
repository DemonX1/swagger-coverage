package com.github.viclovsky.swagger.coverage.core.rule.parameter;

import com.github.viclovsky.swagger.coverage.core.model.Condition;
import com.github.viclovsky.swagger.coverage.core.model.SinglePredicateCondition;
import com.github.viclovsky.swagger.coverage.core.predicate.ConditionPredicate;
import com.github.viclovsky.swagger.coverage.core.predicate.DefaultParameterConditionPredicate;
import io.swagger.v3.oas.models.parameters.Parameter;

public class NotEmptyParameterRule extends ParameterConditionRule {

    @Override
    public String getId() {
        return "parameter-not-empty";
    }

    @Override
    public Condition processParameter(Parameter parameter) {
        if (skip(parameter)) {
            return null;
        }

        ConditionPredicate predicate = new DefaultParameterConditionPredicate(false, parameter.getName(), parameter.getIn());
        return new SinglePredicateCondition(
                String.format("%s «%s» is not empty", parameter.getIn(), parameter.getName()),
                "",
                predicate
        );
    }

    protected boolean skip(Parameter parameter) {
        if (this.options == null) {
            return false;
        }
        if (this.options.getFilter() != null
                && !this.options.getFilter().isEmpty()
                && !this.options.getFilter().contains(parameter.getIn())
        ) {
            return true;
        }

        return this.options.getIgnore() != null
                && !this.options.getIgnore().isEmpty()
                && this.options.getIgnore().contains(parameter.getIn());
    }
}
