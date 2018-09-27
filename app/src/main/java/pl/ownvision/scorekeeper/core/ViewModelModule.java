package pl.ownvision.scorekeeper.core;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.ownvision.scorekeeper.viewmodels.*;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GameListViewModel.class)
    abstract ViewModel bindGameListViewModel(GameListViewModel gameListViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(GameViewModel.class)
    abstract ViewModel bindGameViewModel(GameViewModel gameViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(AppViewModelFactory factory);
}