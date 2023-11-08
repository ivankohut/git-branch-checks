ruleset {
    ruleset('rulesets/basic.xml')
    ruleset('rulesets/braces.xml')
    ruleset('rulesets/comments.xml')
    ruleset('rulesets/convention.xml') {
        exclude 'CompileStatic'
        exclude 'NoDef'
        exclude 'VariableTypeRequired'
    }
    ruleset('rulesets/design.xml')
    ruleset('rulesets/dry.xml')
    ruleset('rulesets/enhanced.xml')
    ruleset('rulesets/exceptions.xml')
    ruleset('rulesets/formatting.xml') {
        exclude 'SpaceAroundMapEntryColon'
        // Line length is checked globally by checkstyle
        exclude 'LineLength'
    }
    ruleset('rulesets/generic.xml')
    ruleset('rulesets/groovyism.xml')
    ruleset('rulesets/imports.xml')
    ruleset('rulesets/junit.xml')
    ruleset('rulesets/naming.xml')
    ruleset('rulesets/size.xml') {
        exclude 'AbcMetric'
    }
    ruleset('rulesets/unnecessary.xml')
    ruleset('rulesets/unused.xml')
}
