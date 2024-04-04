package it.unimib.greenway.ui.main;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import it.unimib.greenway.R;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private NavController navController;
    private SparseArray<Integer> menuDestinationMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_main);
        navController = navHostFragment.getNavController();

        menuDestinationMap = new SparseArray<>();
        menuDestinationMap.put(R.id.item_map, R.id.mapsFragment);
        menuDestinationMap.put(R.id.item_nav, R.id.navigatorFragment);
        menuDestinationMap.put(R.id.item_challenge, R.id.challengeFragment);
        menuDestinationMap.put(R.id.item_user, R.id.userFragment);
        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Integer destinationId = menuDestinationMap.get(item.getItemId());
                if (destinationId != null) {
                    navController.popBackStack();
                    navController.navigate(destinationId);
                    return true;
                }
                return false;
            }
        });




    }


}
