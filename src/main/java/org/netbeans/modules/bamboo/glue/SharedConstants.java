package org.netbeans.modules.bamboo.glue;

/**
 * Constants shared across packages.
 */
public interface SharedConstants {

    /**
     * Path used to load actions for the server instance. A
     * {@code BambooInstance} object should be in the context lookup. May be
     * used e.g. for the context menu of an instance node.
     *
     * @see ActionReference#path
     * @since 1.12
     */
    String ACTION_PATH = "org-netbeans-modules-bamboo/Actions/instance";
}
