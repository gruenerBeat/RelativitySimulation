package engine.rendering;

import game.rendering.NumericRenderer;

public enum RenderType {
    SOFTWARE(Software.class),
    RAY_TRACING(RayTracing.class),
    NUMERIC(NumericRenderer.class);

    private final Class<? extends Renderer> renderClass;

    private RenderType(Class<? extends Renderer> renderClass) {
        this.renderClass = renderClass;
    }

    public Class<? extends Renderer> getRenderClass() {
        return renderClass;
    }
}
