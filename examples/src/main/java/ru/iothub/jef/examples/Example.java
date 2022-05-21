

package ru.iothub.jef.examples;

/**
 * Simple interface to execute examples of Java Embedded Framework API usage
 */
public interface Example {
    /**
     * Gets name of example
     * @return name of example
     */
    String getName();

    /**
     * Initialize example code if required
     * @throws Exception if something going wrong
     */
    void init() throws Exception;

    /**
     * Execute example
     * @throws Exception if something going wrong
     */
    void execute() throws Exception;

    /**
     * Show some intro information if required
     */
    void showIntro();
}
