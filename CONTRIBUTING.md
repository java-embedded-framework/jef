# Contributing to Java Embedded Framework

You want to contribute to Java Embedded Framework? Welcome! Please read this document to understand what you can do:
* [Help Others](#help-others)
* [Improve documentation](#improve-documentation)
* [Analyze Issues](#analyze-issues)
* [Report an Issue](#report-an-issue)
* [Contribute Code](#contribute-code)

## Help Others

You can help Java Embedded Framework by helping others who use it and need support.

## Improve Documentation
You can help Java Embedded Framework by helping to improve quality of documentation (javadoc/guides)

## Analyze Issues
Analyzing issue reports can be a lot of effort. Any help is welcome!
Go to [the GitHub issue tracker](https://github.com/java-embedded-framework/jef/issues?q=is%3Aopen) and find an open issue which needs additional work or a bugfix (e.g. issues labeled with "help wanted" or "bug").

Additional work could include any further information, or a gist, or it might be a hint that helps understanding the issue. Maybe you can even find and [contribute](#contribute-code) a bugfix?

## Report an Issue

If you find a bug - behavior of Java Embedded Framework code contradicting your expectation - you are welcome to report it.
We can only handle well-reported, actual bugs, so please follow the guidelines below.

Once you have familiarized with the guidelines, you can go to 
the [GitHub issue tracker for Java Embedded Framework](https://github.com/java-embedded-framework/jef/issues/new) to report the issue.

### Quick Checklist for Bug Reports

Issue report checklist:
* Real, current bug
* No duplicate
* Reproducible
* Good summary
* Well-documented
* Minimal example
* Use the [template](ISSUE_TEMPLATE.md)

### Issue handling process

When an issue is reported, a committer will look at it and either confirm it as a real issue, 
close it if it is not an issue, or ask for more details.

An issue that is about a real bug is closed as soon as the fix is committed.

### Reporting Security Issues

If you find a security issue, please act responsibly and report it not in the public issue 
tracker, but directly to us, so we can fix it before it can be exploited.
Please send the related information to secure@iot-hub.com.

### Usage of Labels

GitHub offers labels to categorize issues. We defined the following labels so far:

Labels for issue categories:
* bug: this issue is a bug in the code
* feature: this issue is a request for a new functionality or an enhancement request
* design: this issue relates to the UI or UX design of the tool

Status of open issues:
* help wanted: the feature request is approved and you are invited to contribute

Status/resolution of closed issues:
* wontfix: while acknowledged to be an issue, a fix cannot or will not be provided

The labels can only be set and modified by committers.

### Issue Reporting Disclaimer

We want to improve the quality of Java Embedded Framework and good bug reports are welcome! 
But our capacity is limited, thus we reserve the right to close or to not process insufficient 
bug reports in favor of those which are very cleanly documented and easy to reproduce. 
Even though we would like to solve each well-documented issue, there is always the chance 
that it will not happen - remember: Java Embedded Framework is Open Source and 
comes without warranty.

Bug report analysis support is very welcome! (e.g. pre-analysis or proposing solutions)

## Contribute Code

You are welcome to contribute code to Java Embedded Framework in order to fix bugs or to 
implement new features.

There are three important things to know:

1.  You must be aware of the [license](https://github.com/java-embedded-framework/jef/blob/main/LICENSE) 
    (which describes contributions) and **agree to the Contributors License Agreement**. 
    This is common practice in all major Open Source projects.
    For company contributors special rules apply. See the respective section below for details.
2.  There are **several requirements regarding code style, quality, and product standards** which 
    need to be met (we also have to follow them). The respective section below gives more details 
    on the coding guidelines.
3.  **Not all proposed contributions can be accepted**. Some features may e.g. just fit a 
    third-party add-on better. The code must fit the overall direction of Java Embedded Framework 
    and really improve it. The more effort you invest, the better you should clarify in 
    advance whether the contribution fits: the best way would be to just open an issue 
    to discuss the feature you plan to implement (make it clear you intend to contribute).

### Contributor License Agreement

When you contribute (code, documentation, or anything else), you have to be aware that 
your contribution is covered by the [same license](https://github.com/java-embedded-framework/jef/blob/main/LICENSE) 
that is applied to Java Embedded Framework itself.
In particular you need to agree to the Individual Contributor License Agreement,
which can be [found here](https://github.com/java-embedded-framework/jef/blob/main/Individual%20Contributor%20License%20Agreement.txt).
(This applies to all contributors, including those contributing on behalf of a company). 
If you agree to its content, you simply can write in the comment to the pull request 
"I agree with Individual Contributor License Agreement.txt for Java Embedded Framework".

#### Company Contributors

If employees of a company contribute code, in **addition** to the individual agreement above, 
there needs to be one company agreement submitted. This is mainly for the protection of the 
contributing employees.

A company representative authorized to do so needs to download, fill, and print
the [Corporate Contributor License Agreement](/Corporate Contributor License Agreement.pdf) form. 
Then either:

-   Scan it and e-mail it to [opensource@iot-hub.ru](mailto:opensource@iot-hub.ru)

### Contribution Content Guidelines

These are some of the rules we try to follow:

-   Apply a clean coding style adapted to the surrounding code, even though we are aware the 
    existing code is not fully clean
-   Use (4)spaces for indentation (except if the modified file consistently uses tabs)
-   Use variable naming conventions like in the other files you are seeing (camelcase)
-   No System.out.print() - use logging service
-   Run the code check and make it succeed
-   Comment your code where it gets non-trivial
-   Keep an eye on performance and memory consumption, properly destroy objects when not used anymore
-   Write a unit test
-   Do not do any incompatible changes, especially do not modify the name or behavior of 
    public API methods or properties

### How to contribute - the Process

1.  Make sure the change would be welcome (e.g. a bugfix or a useful feature); 
    best do so by proposing it in a GitHub issue
2.  Create a branch forking the cla-assistant repository and do your change
3.  Commit and push your changes on that branch
4.  In the commit message
- Describe the problem you fix with this change.
- Describe the effect that this change has from a user's point of view. App crashes and lockups 
  are pretty convincing for example, but not all bugs are that obvious and should be mentioned 
  in the text.
- Describe the technical details of what you changed. It is important to describe the change 
  in a most understandable way so the reviewer is able to verify that the code is behaving 
  as you intend it to.
5.  If your change fixes an issue reported at GitHub, add the following line to the commit message:
    - ```Fixes #(issueNumber)```
    - Do NOT add a colon after "Fixes" - this prevents automatic closing.
6.  Create a Pull Request
7.  Follow the link posted by the Java Embedded Framework to your pull request and accept it, 
    as described in detail above.
8.  Wait for our code review and approval, possibly enhancing your change on request
    -   Note that the Java Embedded Framework developers also have their regular duties, 
        so depending on the required effort for reviewing, testing and clarification this may 
        take a while

9.  Once the change has been approved we will inform you in a comment
10.  We will close the pull request, feel free to delete the now obsolete branch
