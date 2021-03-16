# git-branch-checks

*Gradle* plugin for validation of GIT branches. Useful for automatically
validating branches during CI build. Available checks (as *Gradle* tasks):
- branch name compliance to given regexp pattern (`checkBranchName` task)
- commit messages compliance to given regexp pattern (`checkCommitMessages` task)
- no merge commits in the branch (`checkNoMergeCommits` task)

## How to Use

In your `build.gradle` file add:
```groovy
plugins {
    id 'com.github.ivankohut.git-branch-checks' version '0.0.3'
}
```

Tasks can be configured using `gitBranchChecks` closure:
```groovy
gitBranchChecks {
    branchPattern = ~/(master)|(feature\\\/#\d+\\\/[a-z0-9\-]+)/
    messagePattern = ~/ABC-\d+ .*/
}
```

Executing checks as part of `check` task:
```groovy
check {
    dependsOn checkBranchName, checkCommitMessages, checkNoMergeCommits
}
```

Disabling unwanted checks:
```groovy
checkNoMergeCommits.enabled = false
```

In case you run your builds on Travis CI, add `git fetch origin master:master` command to `script` part of `travis.yml` before checks execution:  
```yaml
...
script:
  - git fetch origin master:master
  - ./gradlew --info checkBranchName checkCommitMessages checkNoMergeCommits
...
```
