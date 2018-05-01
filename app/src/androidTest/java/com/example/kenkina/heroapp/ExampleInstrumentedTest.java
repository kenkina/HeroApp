package com.example.kenkina.heroapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.example.kenkina.heroapp.view.activities.MainActivity;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static org.junit.Assert.*;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    // Inicializa el test desde MainActivity
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);


    // onView(ViewMatcher): encuentra la vista (withId, withText, isChecked, ...).
    //.perform(ViewAction): acciones a realizar (click, typeText, swipeLeft, ...).
    //.check(ViewAssertion): comprueba resultado
    @Test
    public void useAppContext() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.kenkina.heroapp", appContext.getPackageName());
    }

    @Test
    public void createTenHeroes() {
        int quantityNewHeroes = 30;
        int quantityBefore = HeroApplication.getInstance().getDatabase().mHeroDao().getHeroesCount();
        int quantityExpected = quantityBefore + quantityNewHeroes;

        for (int i = 0; i < quantityNewHeroes; i++) {
            createHero();
        }

        int quantityAfter = HeroApplication.getInstance().getDatabase().mHeroDao().getHeroesCount();
        assertEquals(quantityExpected, quantityAfter);
    }


    public void createHero() {
        int quantityBefore = HeroApplication.getInstance().getDatabase().mHeroDao().getHeroesCount();
        int quantityExpected = quantityBefore + 1;
        int idNew = quantityBefore + 1;

        onView(withId(R.id.mainFab))
                .perform(click());

        onView(withId(R.id.idEditText))
                .perform(typeText("" + idNew),
                        ViewActions.closeSoftKeyboard());

        onView(withId(R.id.nameEditText))
                .perform(typeText("Hero N" + idNew),
                        ViewActions.closeSoftKeyboard());

        onView(withId(R.id.newHeroFab))
                .perform(click());

        //onView(isRoot())
        //        .perform(waitFor(3000));

        int quantityAfter = HeroApplication.getInstance().getDatabase().mHeroDao().getHeroesCount();
        assertEquals(quantityExpected, quantityAfter);
    }

    /**
     * Perform action of waiting for a specific time.
     */
    public static ViewAction waitFor(final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }
}
