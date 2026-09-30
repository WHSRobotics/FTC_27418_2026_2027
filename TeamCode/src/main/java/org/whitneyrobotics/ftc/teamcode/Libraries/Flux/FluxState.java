// Written by: Christopher Gholmieh
// Package:
package org.whitneyrobotics.ftc.teamcode.Libraries.Flux;

// Imports:

import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.function.BooleanSupplier;

// State:
public class FluxState<S extends Enum<S>> {
    // Variables (Assignment):
    // Enter:
    public Runnable on_enter = () -> {};

    // Update:
    public Runnable on_update = () -> {};

    // Exit:
    public Runnable on_exit = () -> {};

    // Condition:
    public BooleanSupplier conditional = () -> true;

    // Variables (Declaration):
    // Next:
    public S next_state;

    // Timer:
    private ElapsedTime timer;

    // Methods:
    public void enter() {
        // Variables (Assignment):
        // Timer:
        timer = new ElapsedTime();

        // Logic:
        on_enter.run();
    }

    public void update() {
        on_update.run();
    }

    public void exit() {
        on_exit.run();
    }

    public boolean is_finished() {
        return conditional.getAsBoolean();
    }

    public double time() {
        return timer.seconds();
    }
}