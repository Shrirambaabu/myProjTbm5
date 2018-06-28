package com.forzo.holdMyCard.ui.activities.addparticipant;


import com.forzo.holdMyCard.base.PerActivityScope;

import dagger.Component;

@Component(modules = {AddParticipantModule.class})
@PerActivityScope
public interface AddParticipantComponent {

    void inject(AddParticipantActivity addParticipantActivity);

}
