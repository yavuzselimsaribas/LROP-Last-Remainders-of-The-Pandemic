package com.cs102.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

public class WorldContactListener implements ContactListener{
    //public boolean isItemCollectable;
    @Override
    public void beginContact(Contact contact) {
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        /*
        if (fixtureA.getBody().getUserData().equals("ITEM")) isItemCollectable = true;
        else if (fixtureB.getBody().getUserData().equals("ITEM")) isItemCollectable = true;
        else isItemCollectable = false;
        */



        Gdx.app.debug("CONTACT", "BEGIN: " + fixtureA.getBody().getUserData() + " " + fixtureA.isSensor());
        Gdx.app.debug("CONTACT", "BEGIN: " + fixtureB.getBody().getUserData() + " " + fixtureB.isSensor());
    }

    @Override
    public void endContact(Contact contact) {
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        Gdx.app.debug("CONTACT", "END: " + fixtureA.getBody().getUserData() + " " + fixtureA.isSensor());
        Gdx.app.debug("CONTACT", "END: " + fixtureB.getBody().getUserData() + " " + fixtureB.isSensor());
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        //contact.setEnabled(false);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
