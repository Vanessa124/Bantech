package br.com.senaijandira.controlefinanceiro;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class EstatisticasActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPagerEstatisticas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estatisticas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Estatísticas");

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPagerEstatisticas = (ViewPager) findViewById(R.id.viewPagerEstatisticas);

        ViewPagerEstatisticasAdapter vpAdapter = new ViewPagerEstatisticasAdapter(getSupportFragmentManager());
        viewPagerEstatisticas.setAdapter(vpAdapter);
        tabLayout.setupWithViewPager(viewPagerEstatisticas);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public class ViewPagerEstatisticasAdapter extends FragmentPagerAdapter {

        /*Construtor da classe*/
        public ViewPagerEstatisticasAdapter (FragmentManager fragmentManager){
            super(fragmentManager);
        }

        @Override
        public CharSequence getPageTitle(int tabAtual) {
            /*Setando os títulos*/
            if(tabAtual == 0){
                return "Categoria";
            } else if(tabAtual == 1){
                return "Mês";
            }

            return null;
        }

        @Override
        public Fragment getItem(int tabAtual) {
            if(tabAtual == 0){
                CategoriaFragment categoriaFragment = new CategoriaFragment();
                return categoriaFragment;
            } else if(tabAtual == 1){
                MesFragment mesFragment = new MesFragment();
                return mesFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }

}
