package com.cs102.game.ui;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.profiling.GLProfiler;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.cs102.game.LastRemaindersOfThePandemic;
import com.cs102.game.ecs.ECSEngine;
import com.cs102.game.ecs.components.AnimationComponent;
import com.cs102.game.ecs.components.B2DComponent;
import com.cs102.game.ecs.components.GameObjectComponent;
import com.cs102.game.map.Map;
import com.cs102.game.map.MapListener;

import java.util.EnumMap;

import static com.cs102.game.LastRemaindersOfThePandemic.UNIT_SCALE;
import static com.cs102.game.LastRemaindersOfThePandemic.alpha;

public class GameRenderer implements Disposable, MapListener {
    public static final String TAG = GameRenderer.class.getSimpleName();

    private final OrthographicCamera gameCamera;
    private final Viewport viewport;
    private final SpriteBatch spriteBatch;
    private final AssetManager assetManager;
    private final EnumMap<AnimationType, Animation<Sprite>> animationCache;
    private final ImmutableArray<Entity> gameObjectEntities;
    private final ImmutableArray<Entity> animatedEntities;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Array<TiledMapTileLayer> tiledMapLayers;

    private final GLProfiler profiler;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private final World world;
    private IntMap<Animation<Sprite>> mapAnimations;
    private ObjectMap<String, TextureRegion[][]> regionCache;


    public GameRenderer(final LastRemaindersOfThePandemic mainGame) {
        assetManager = mainGame.getAssetManager();

        viewport = mainGame.getViewport();
        viewport.setWorldHeight(9);
        viewport.setWorldWidth(16);

        gameCamera = mainGame.getGameCamera();
        spriteBatch = mainGame.getSpriteBatch();
        animationCache = new EnumMap<>(AnimationType.class);
        regionCache = new ObjectMap<String, TextureRegion[][]>();

        gameObjectEntities = mainGame.getEcsEngine().getEntitiesFor(Family.all(GameObjectComponent.class, B2DComponent.class, AnimationComponent.class).get());
        animatedEntities = mainGame.getEcsEngine().getEntitiesFor(Family.all(AnimationComponent.class, B2DComponent.class).exclude(GameObjectComponent.class).get());

        mapRenderer = new OrthogonalTiledMapRenderer(null, UNIT_SCALE, spriteBatch);
        mainGame.getMapManager().addMapListener(this);
        //mapRenderer.setMap(mainGame.getMapManager().getTiledMap());
        tiledMapLayers = new Array<TiledMapTileLayer>();
        //mapAnimations = mainGame.getMapManager().getCurrentMap().getMapAnimations();

        //mapAnimations = mainGame.getMapManager().getCurrentMap().getMapAnimations();

        profiler = new GLProfiler(Gdx.graphics);
        profiler.enable();

        if(profiler.isEnabled()) {
            box2DDebugRenderer = new Box2DDebugRenderer();
            world = mainGame.getWorld();
        }
        else {
            box2DDebugRenderer = null;
            world = null;
        }
    }
    @Override
    public void dispose() {
        if (box2DDebugRenderer != null) {
            box2DDebugRenderer.dispose();
        }
        mapRenderer.dispose();
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply(false);

        mapRenderer.setView(gameCamera);
        spriteBatch.begin();
        
        if (mapRenderer.getMap() != null) {
            AnimatedTiledMapTile.updateAnimationBaseTime();
            for (final TiledMapTileLayer layer : tiledMapLayers) {
                mapRenderer.renderTileLayer(layer);
            }
        }

        for (final Entity entity : gameObjectEntities) {
            renderGameObject(entity, alpha);
        }

        for (final Entity entity : animatedEntities) {
            renderEntity(entity, alpha);
            final B2DComponent b2DComponent = ECSEngine.b2dCmpMapper.get(entity);
        }
        spriteBatch.end();
        profiler.enable();
        if (profiler.isEnabled()) {
            profiler.reset();
            box2DDebugRenderer.render(world, viewport.getCamera().combined);
        }
    }

    private void renderGameObject(final Entity entity, final float alpha) {
        final B2DComponent b2DComponent = ECSEngine.b2dCmpMapper.get(entity);
        final AnimationComponent animationComponent = ECSEngine.animationCmpMapper.get(entity);
        final GameObjectComponent gameObjectComponent = ECSEngine.gameObjCmpMapper.get(entity);

        if (gameObjectComponent.animationIndex != -1) {
            final Animation<Sprite> animation = mapAnimations.get(gameObjectComponent.animationIndex);
            final Sprite frame = animation.getKeyFrame(animationComponent.animationTime);

            frame.setBounds(b2DComponent.renderPosition.x, b2DComponent.renderPosition.y, animationComponent.width, animationComponent.height);
            frame.setOriginCenter();
            frame.setRotation(b2DComponent.body.getAngle() * MathUtils.radDeg);
            frame.draw(spriteBatch);
        }
    }

    private void renderEntity(final Entity entity, final float alpha) {
        final B2DComponent b2DComponent = ECSEngine.b2dCmpMapper.get(entity);
        final AnimationComponent animationComponent = ECSEngine.animationCmpMapper.get(entity);

        if(animationComponent.animationType != null) {
            final Animation<Sprite> animation = getAnimation(animationComponent.animationType);
            final Sprite frame = animation.getKeyFrame(animationComponent.animationTime);
            b2DComponent.renderPosition.lerp(b2DComponent.body.getPosition(), alpha);
            frame.setBounds(b2DComponent.renderPosition.x - animationComponent.width * 0.5f, b2DComponent.renderPosition.y - b2DComponent.height * 0.5f, animationComponent.width, animationComponent.height);
            frame.draw(spriteBatch);
        }

    }

    private Animation<Sprite> getAnimation(final AnimationType animationType) {
        Animation<Sprite> animation = animationCache.get(animationType);
        if (animation == null) {
            //create animation
            Gdx.app.debug(TAG, "Creating new animation of the type " + animationType);
            TextureRegion[][] textureRegions = regionCache.get(animationType.getAtlasKey());
            if (textureRegions == null) {
                Gdx.app.debug(TAG, "Creating texture regions for " + animationType.getAtlasKey());
                final TextureAtlas.AtlasRegion atlasRegion = assetManager.get(animationType.getAtlasPath(), TextureAtlas.class).findRegion(animationType.getAtlasKey());
                textureRegions = atlasRegion.split(64, 64);
                regionCache.put(animationType.getAtlasKey(), textureRegions);
            }
            //split the atlas region into frames
            animation = new Animation<Sprite>(animationType.getFrameDuration(), getKeyFrames(textureRegions[animationType.getFrameIndex()]));
            animation.setPlayMode(Animation.PlayMode.LOOP);
            animationCache.put(animationType, animation);
        }
        return animation;
    }

    private Sprite[] getKeyFrames(final TextureRegion[] textureRegion) {
        final Sprite[] keyFrames = new Sprite[textureRegion.length];

        int i = 0;
        for (final TextureRegion region : textureRegion) {
            final Sprite sprite = new Sprite(region);
            sprite.setOriginCenter();
            keyFrames[i++] = sprite;
        }
        return keyFrames;
    }

    @Override
    public void mapChange(Map map) {
        mapRenderer.setMap(map.getTiledMap());
        map.getTiledMap().getLayers().getByType(TiledMapTileLayer.class, tiledMapLayers);
        mapAnimations = map.getMapAnimations();
    }
}
