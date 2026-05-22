package com.healthhub.healthhub_backend.dto;

public class AdminStatsResponse {

    private long totalUsers;
    private long totalBooks;
    private long totalArticles;
    private long totalDonations;
    private long openDonations;
    private long totalCampaigns;
    private long activeCampaigns;
    private long totalSubscribers;

    public AdminStatsResponse(long totalUsers, long totalBooks, long totalArticles,
                              long totalDonations, long openDonations,
                              long totalCampaigns, long activeCampaigns,
                              long totalSubscribers) {
        this.totalUsers = totalUsers;
        this.totalBooks = totalBooks;
        this.totalArticles = totalArticles;
        this.totalDonations = totalDonations;
        this.openDonations = openDonations;
        this.totalCampaigns = totalCampaigns;
        this.activeCampaigns = activeCampaigns;
        this.totalSubscribers = totalSubscribers;
    }

    public long getTotalUsers() { return totalUsers; }
    public long getTotalBooks() { return totalBooks; }
    public long getTotalArticles() { return totalArticles; }
    public long getTotalDonations() { return totalDonations; }
    public long getOpenDonations() { return openDonations; }
    public long getTotalCampaigns() { return totalCampaigns; }
    public long getActiveCampaigns() { return activeCampaigns; }
    public long getTotalSubscribers() { return totalSubscribers; }
}