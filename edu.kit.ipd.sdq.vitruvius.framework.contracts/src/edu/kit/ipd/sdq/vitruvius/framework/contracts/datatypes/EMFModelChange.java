package edu.kit.ipd.sdq.vitruvius.framework.contracts.datatypes;

import edu.kit.ipd.sdq.vitruvius.framework.contracts.meta.change.EChange;

public class EMFModelChange extends Change implements URIHaving {
    private VURI vuri;
    private EChange eChange;

    public EMFModelChange(final EChange eChange, final VURI vuri) {
        this.vuri = vuri;
        this.eChange = eChange;
    }

    public EChange getEChange() {
        return this.eChange;
    }

    @Override
    public VURI getURI() {
        return this.vuri;
    }

    @Override
    public String toString() {
        return EMFModelChange.class.getSimpleName() + ": VURI: " + this.vuri + " EChange: " + this.eChange;
    }
}
