package pl.ownvision.scorekeeper.core;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import pl.ownvision.scorekeeper.viewmodels.GameListViewModel;

/**
 * Created by Jakub on 26.06.2017.
 */

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(GameListViewModel.class)
    abstract ViewModel bindGameListViewModel(GameListViewModel gameListViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(AppViewModelFactory factory);
}