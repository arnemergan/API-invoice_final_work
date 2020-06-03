package com.api.invoice.services;

import com.api.invoice.dto.response.Stats;

public interface StatisticsService {
    public Stats getStats(String token);
    public Stats getStatsOnCategory(String token, String name);
    public Stats getStatsOnUsername(String token, String username);
}
