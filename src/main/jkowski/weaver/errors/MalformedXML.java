package jkowski.weaver.errors;

/**
 * Used when the parse encounters a malformed XML file
 */
public class MalformedXML extends RuntimeException {
  /**
   * MalformedXML
   * @param message message
   */
  public MalformedXML(String message) {
        super(message);
    }
}
