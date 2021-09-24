package com.rdthelper.rdthelper.Repositories;
import com.rdthelper.rdthelper.Models.Settings;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface SettingsRepository extends JpaRepository<Settings, Long> {
}
